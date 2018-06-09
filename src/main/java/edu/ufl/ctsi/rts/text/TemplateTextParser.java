package edu.ufl.ctsi.rts.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;

import edu.uams.dbmi.rts.template.RtsTemplate;
import edu.uams.dbmi.rts.time.TemporalReference;


public class TemplateTextParser {
	protected BufferedReader r;
	
	HashSet<RtsTemplate> templates;
	HashSet<TemporalReference> temporalReferences;
	
	public TemplateTextParser(BufferedReader r) {
		this.r = r;
		templates = new HashSet<RtsTemplate>();
		temporalReferences = new HashSet<TemporalReference>();
	}
	
	public void parseTemplates() throws IOException, ParseException {
		
			int readSize = 64;
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
		/*
		 * Split on non-escaped template block delimiter and the delimiter
		 * 	cannot be inside quotes either.
		 */
		char[] templateChars = templateText.toCharArray();
		
		String[] blocks = new String[2];
		boolean inside_quote = false;
		char lastChar = '\0';
		boolean found = false;
		for (int i=0; i<templateChars.length; i++) {
			char c = templateChars[i];
			/*
			 * We should have only one of these
			 */
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
	}
}
