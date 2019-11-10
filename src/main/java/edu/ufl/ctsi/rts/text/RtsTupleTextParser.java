package edu.ufl.ctsi.rts.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.uams.dbmi.rts.RtsDeclaration;
import edu.uams.dbmi.rts.RtsTupleFactory;
import edu.uams.dbmi.rts.time.TemporalRegion;
import edu.uams.dbmi.rts.tuple.RtsTuple;


public class RtsTupleTextParser implements Iterable<RtsTuple> {
	
	protected BufferedReader r;
	
	HashSet<RtsTuple> Tuples;
	HashSet<TemporalRegion> temporalRegions;
	
	protected RtsTupleFactory tFactory;	
	
	public RtsTupleTextParser(BufferedReader r) {
		this.r = r;
		Tuples = new HashSet<RtsTuple>();
		temporalRegions = new HashSet<TemporalRegion>();
		tFactory = new RtsTupleFactory();
	}
	
	public void parseTuples() throws IOException, ParseException {
		
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
					if (read[j] == RtsTupleTextWriter.QUOTE_OPEN && prior_read != RtsTupleTextWriter.ESCAPE) {
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
					else if (read[j] == RtsTupleTextWriter.QUOTE_CLOSE 
								&& prior_read != RtsTupleTextWriter.ESCAPE) {
						if (inside_quote) {
							inside_quote = false;
							sb.append(read[j]);
						} else {
							throw new ParseException("Cannot close quoted text without corresponding open quote.", position);
						}
					} 
					/*
					 * A Tuple_DELIM character terminates a Tuple iff we are not 
					 * 	inside a quote and it is not escaped.
					 */
					else if (read[j] == RtsTupleTextWriter.TUPLE_DELIM && prior_read != '\\' && !inside_quote) {
						String Tuple = sb.toString();
						parseTupleFromText(Tuple);
						System.out.println("Tuple = \"" + sb.toString() + "\"");
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
	
	protected void parseTupleFromText(String TupleText) throws ParseException {
		
		List<String> blocks = splitDelimitedQuotedAndEscapedText(TupleText, 
				RtsTupleTextWriter.BLOCK_DELIM,
				RtsTupleTextWriter.QUOTE_OPEN,
				RtsTupleTextWriter.QUOTE_CLOSE,
				RtsTupleTextWriter.ESCAPE);
	
		if (blocks.size() != 2) System.err.println("INCORRECT NUMBER OF BLOCKS!!! " + blocks.size());
		
		String tupleBlock = blocks.get(0);
		String contentBlock = blocks.get(1);
		
		List<String> tupleFields = splitDelimitedQuotedAndEscapedText(tupleBlock, 
				RtsTupleTextWriter.FIELD_DELIM,
				RtsTupleTextWriter.QUOTE_OPEN,
				RtsTupleTextWriter.QUOTE_CLOSE,
				RtsTupleTextWriter.ESCAPE);
		
		List<String> contentFields = splitDelimitedQuotedAndEscapedText(contentBlock, 
				RtsTupleTextWriter.FIELD_DELIM,
				RtsTupleTextWriter.QUOTE_OPEN,
				RtsTupleTextWriter.QUOTE_CLOSE,
				RtsTupleTextWriter.ESCAPE);
		
		RtsDeclaration Tuple = tFactory.buildRtsTupleOrTemporalRegion(tupleFields, contentFields);
		
	
		
		if (Tuple instanceof RtsTuple) {
			RtsTuple t = (RtsTuple)Tuple;
			Tuples.add(t);
		} else if (Tuple instanceof TemporalRegion) {
			TemporalRegion tr = (TemporalRegion)Tuple;
			temporalRegions.add(tr);
		}
	}
 
	public static List<String> splitDelimitedQuotedAndEscapedText(String text, char delim, char openQuote, 
			char closeQuote, char escape) throws ParseException {
		ArrayList<String> fragments = new ArrayList<String>(text.length()/2);
		/*
		 * Split on non-escaped Tuple block delimiter and the delimiter
		 * 	cannot be inside quotes either.
		 */
		char[] textChars = text.toCharArray();
		
		boolean inside_quote = false;
		char lastChar = '\0';
		int begin = 0;
		for (int i=0; i<textChars.length; i++) {
			char c = textChars[i];

			if (c == delim && lastChar != escape && !inside_quote) {
				String nextFragment = text.substring(begin,i);
				if (nextFragment.indexOf(openQuote) == 0 && 
						nextFragment.indexOf(closeQuote) == nextFragment.length()-1) 
					nextFragment = nextFragment.substring(1, nextFragment.length()-1);
				fragments.add(nextFragment);  //gets every character up to ith character, but not including it
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

	@Override
	public Iterator<RtsTuple> iterator() {
		return Tuples.iterator();
	}
	
	public Set<TemporalRegion> getTemporalRegions() {
		return temporalRegions;
	}
}
