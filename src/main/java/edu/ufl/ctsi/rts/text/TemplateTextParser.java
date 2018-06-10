package edu.ufl.ctsi.rts.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.template.ATemplate;
import edu.uams.dbmi.rts.template.MetadataTemplate;
import edu.uams.dbmi.rts.template.PtoCTemplate;
import edu.uams.dbmi.rts.template.PtoDETemplate;
import edu.uams.dbmi.rts.template.PtoLackUTemplate;
import edu.uams.dbmi.rts.template.PtoPTemplate;
import edu.uams.dbmi.rts.template.PtoUTemplate;
import edu.uams.dbmi.rts.template.RtsTemplate;
import edu.uams.dbmi.rts.time.TemporalReference;
import edu.uams.dbmi.util.iso8601.Iso8601DateParseException;
import edu.uams.dbmi.util.iso8601.Iso8601DateTimeParser;
import edu.uams.dbmi.util.iso8601.Iso8601TimeParseException;


public class TemplateTextParser {
	protected BufferedReader r;
	
	HashSet<RtsTemplate> templates;
	HashSet<TemporalReference> temporalReferences;
	
	protected Iso8601DateTimeParser dt_parser;
	
	public TemplateTextParser(BufferedReader r) {
		this.r = r;
		templates = new HashSet<RtsTemplate>();
		temporalReferences = new HashSet<TemporalReference>();
		
		dt_parser = new Iso8601DateTimeParser();
	}
	
	public void parseTemplates() throws IOException, ParseException {
		
		/* Here, we cannot use the generic parsing method because we are only
		 * pulling in text in 64 character chunks at a time.
		 */
			int readSize = 8192;
			char[] read = new char[readSize];
				
			int i = 1;
			char prior_read = '\0';
			StringBuilder sb = new StringBuilder();
			boolean inside_quote = false;
			int position = 0;
			while (i > 0) {
				i = r.read(read, 0, 64);
				//System.out.println(read);
				for (int j=0; j<i; j++) {
					/*
					 * If we get an opening quote character, and it is not escaped, then we have
					 * 	to open a quote, or if we're already in a quote, signal an error
					 */
					if (read[j] == TemplateTextWriter.QUOTE_OPEN && prior_read != TemplateTextWriter.ESCAPE) {
						if (!inside_quote) {
							inside_quote = true;
							sb.append(read[j]);
						} else {
							//we can't have an open quote that is unescaped
							throw new ParseException("Cannot open quoted text within quoted text.", position);
						}
					}
					/*
					 * If we get a closing quote character, and it is not escaped, then we have
					 *   to close the quote, or if we're not in a quote, signal an error.
					 */
					else if (read[j] == TemplateTextWriter.QUOTE_CLOSE 
								&& prior_read != TemplateTextWriter.ESCAPE) {
						if (inside_quote) {
							inside_quote = false;
							sb.append(read[j]);
						} else {
							throw new ParseException("Cannot close quoted text without corresponding open quote.", position);
						}
					} 
					/*
					 * A TEMPLATE_DELIM character terminates a template iff we are not 
					 * 	inside a quote and it is not escaped.
					 */
					else if (read[j] == TemplateTextWriter.TEMPLATE_DELIM && prior_read != '\\' && !inside_quote) {
						String template = sb.toString();
						parseTemplateFromText(template);
						System.out.println("Template = \"" + sb.toString() + "\"");
						sb = new StringBuilder();
						prior_read = '\0';
					} else {
						sb.append(read[j]);
						prior_read = read[j];
					}
					position++;
				}
			}
	}
	
	protected void parseTemplateFromText(String templateText) throws ParseException {
		
		List<String> blocks = splitDelimitedQuotedAndEscapedText(templateText, 
				TemplateTextWriter.BLOCK_DELIM,
				TemplateTextWriter.QUOTE_OPEN,
				TemplateTextWriter.QUOTE_CLOSE,
				TemplateTextWriter.ESCAPE);
		System.out.println("There are " + blocks.size() + " blocks.");
		if (blocks.size() != 2) System.err.println("INCORRECT NUMBER OF BLOCKS!!! " + blocks.size());
		
		String templateBlock = blocks.get(0);
		String contentBlock = blocks.get(1);
		
		List<String> templateFields = splitDelimitedQuotedAndEscapedText(templateBlock, 
				TemplateTextWriter.FIELD_DELIM,
				TemplateTextWriter.QUOTE_OPEN,
				TemplateTextWriter.QUOTE_CLOSE,
				TemplateTextWriter.ESCAPE);
		
		List<String> contentFields = splitDelimitedQuotedAndEscapedText(contentBlock, 
				TemplateTextWriter.FIELD_DELIM,
				TemplateTextWriter.QUOTE_OPEN,
				TemplateTextWriter.QUOTE_CLOSE,
				TemplateTextWriter.ESCAPE);
		
		String tupleType = templateFields.get(0);
		RtsTemplate template = null;
		TemporalReference temporalReference = null;
		if (tupleType.equals("A")) {
			template = new ATemplate();		
		} else if (tupleType.equals("U")) {
			template = new PtoUTemplate();
		} else if (tupleType.equals("P")) {
			template = new PtoPTemplate();
		} else if (tupleType.equals("L")) {
			template = new PtoLackUTemplate();
		} else if (tupleType.equals("C")) {
			template = new PtoCTemplate();
		} else if (tupleType.equals("E")) {
			template = new PtoDETemplate();
		} else if (tupleType.equals("D")) {
			template = new MetadataTemplate();
		} else if (tupleType.equals("T")) {
			
		} else {
			throw new RuntimeException("Unrecognized tuple type: " + tupleType);
		}
		
		if (template != null) {
			template.setTemplateIui(Iui.createFromString(templateFields.get(1)));
			template.setAuthorIui(Iui.createFromString(contentFields.get(0)));
			populateTemplate(template, contentFields);
		}
		
		for (String s : blocks) {
			//System.out.println("\t" + s);
			List<String> tupleElements = splitDelimitedQuotedAndEscapedText(s, 
					TemplateTextWriter.FIELD_DELIM,
					TemplateTextWriter.QUOTE_OPEN,
					TemplateTextWriter.QUOTE_CLOSE,
					TemplateTextWriter.ESCAPE);
			System.out.println("\tBlock has " + tupleElements.size() + " fields in it.");
		}
		/*
		 * Split on non-escaped template block delimiter and the delimiter
		 * 	cannot be inside quotes either.
		 
		char[] templateChars = templateText.toCharArray();
		
		String[] blocks = new String[2];
		boolean inside_quote = false;
		char lastChar = '\0';
		boolean found = false;
		for (int i=0; i<templateChars.length; i++) {
			char c = templateChars[i];
			/*
			 * We should have only one of these
			 
			if (c == TemplateTextWriter.BLOCK_DELIM && lastChar != TemplateTextWriter.ESCAPE
					&& !inside_quote) {
				if (!found) {
					blocks[0] = templateText.substring(0,i);
					blocks[1] = templateText.substring(i+1);
					found = true;
				} else {
					throw new ParseException("Template text should have only two blocks: " + templateText, i);
				}
			} else if (c == TemplateTextWriter.QUOTE_OPEN && lastChar != TemplateTextWriter.ESCAPE) {
				if (!inside_quote) {
					inside_quote = true;
				} else {
					throw new ParseException("Already inside quote. Cannot open quote." + templateText, i);
				}
			} else if (c == TemplateTextWriter.QUOTE_CLOSE && lastChar != TemplateTextWriter.ESCAPE) {
				if (inside_quote) {
					inside_quote = false;
				} else {
					throw new ParseException("Not inside quote. Cannot close quote." + templateText, i);
				}
			}
			lastChar = c;
		}
		System.out.println("BLOCK: " + blocks[0]);
		*/
	}
	
	protected List<String> splitDelimitedQuotedAndEscapedText(String text, char delim, char openQuote, 
			char closeQuote, char escape) throws ParseException {
		ArrayList<String> fragments = new ArrayList<String>(text.length()/2);
		/*
		 * Split on non-escaped template block delimiter and the delimiter
		 * 	cannot be inside quotes either.
		 */
		char[] textChars = text.toCharArray();
		
		boolean inside_quote = false;
		char lastChar = '\0';
		int begin = 0;
		for (int i=0; i<textChars.length; i++) {
			char c = textChars[i];

			if (c == delim && lastChar != escape && !inside_quote) {
				fragments.add(text.substring(begin,i));  //gets every character up to ith character, but not including it
				begin = i+1;		//skip ith character because it's a delimiter		
			} else if (c == openQuote && lastChar != escape) {
				if (!inside_quote) {
					inside_quote = true;
				} else {
					throw new ParseException("Already inside quote. Cannot open quote." + text, i);
				}
			} else if (c == closeQuote && lastChar != escape) {
				if (inside_quote) {
					inside_quote = false;
				} else {
					throw new ParseException("Not inside quote. Cannot close quote." + text, i);
				}
			}
			lastChar = c;
		}
		fragments.add(text.substring(begin));  //the last delimited piece is hanging out here to be collected
		return fragments;
	}
	
	protected void populateTemplate(RtsTemplate t, List<String> contentFields) {
		if (t instanceof ATemplate) {
			populateATemplate((ATemplate)t, contentFields);
		} else if (t instanceof PtoUTemplate) {
			populatePtoUTemplate((PtoUTemplate)t, contentFields);
		} else if (t instanceof PtoPTemplate) {
			populatePtoPTemplate((PtoPTemplate)t, contentFields);
		} else if (t instanceof PtoLackUTemplate) {
			populatePtoLackUTemplate((PtoLackUTemplate)t, contentFields);
		} else if (t instanceof PtoCTemplate) {
			populatePtoCTemplate((PtoCTemplate)t, contentFields);
		} else if (t instanceof PtoDETemplate) {
			populatePtoDETemplate((PtoDETemplate)t, contentFields);
		} else if (t instanceof MetadataTemplate) {
			populateMetadataTemplate((MetadataTemplate)t, contentFields);
		}
	}

	private void populateATemplate(ATemplate t, List<String> contentFields) {
		t.setReferentIui(Iui.createFromString(contentFields.get(2)));
		try {
			t.setAuthoringTimestamp(dt_parser.parse(contentFields.get(1)));
		} catch (Iso8601DateParseException e) {
			e.printStackTrace();
		} catch (Iso8601TimeParseException e) {
			e.printStackTrace();
		}
	}

	private void populatePtoUTemplate(PtoUTemplate t, List<String> contentFields) {
		// TODO Auto-generated method stub
		
	}

	private void populatePtoPTemplate(PtoPTemplate t, List<String> contentFields) {
		// TODO Auto-generated method stub
		
	}

	private void populatePtoLackUTemplate(PtoLackUTemplate t, List<String> contentFields) {
		// TODO Auto-generated method stub
		
	}

	private void populatePtoCTemplate(PtoCTemplate t, List<String> contentFields) {
		// TODO Auto-generated method stub
		
	}

	private void populatePtoDETemplate(PtoDETemplate t, List<String> contentFields) {
		// TODO Auto-generated method stub
		
	}

	private void populateMetadataTemplate(MetadataTemplate t, List<String> contentFields) {
		// TODO Auto-generated method stub
		
	}
}
