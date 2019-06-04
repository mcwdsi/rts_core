package edu.ufl.ctsi.rts.text;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

import edu.uams.dbmi.rts.ParticularReference;
import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.time.TemporalReference;
import edu.uams.dbmi.rts.time.TemporalRegion;
import edu.uams.dbmi.rts.tuple.ATuple;
import edu.uams.dbmi.rts.tuple.MetadataTuple;
import edu.uams.dbmi.rts.tuple.PtoCTuple;
import edu.uams.dbmi.rts.tuple.PtoDETuple;
import edu.uams.dbmi.rts.tuple.PtoLackUTuple;
import edu.uams.dbmi.rts.tuple.PtoPTuple;
import edu.uams.dbmi.rts.tuple.PtoUTuple;
import edu.uams.dbmi.rts.tuple.RtsTuple;
import edu.uams.dbmi.rts.tuple.component.RelationshipPolarity;
import edu.uams.dbmi.util.iso8601.Iso8601DateTimeFormatter;

public class RtsTupleTextWriter {

	public static char FIELD_DELIM = '|';
	public static char SUBFIELD_DELIM = ',';
	public static char BLOCK_DELIM = '~';
	public static char ESCAPE = '\\';
	public static char TUPLE_DELIM = '\n';
	public static char QUOTE_OPEN = '<';
	public static char QUOTE_CLOSE = '>';
	
	protected Writer w;
	
	public RtsTupleTextWriter(Writer w) {
		this.w = w;
	}
	
	public void writeTuple(RtsTuple rtt) throws IOException {
		if (rtt.isATuple()) w.write('A');
		else if (rtt.isMetadataTuple()) w.write('D');
		else if (rtt.isPtoCTuple()) w.write('C');
		else if (rtt.isPtoLackUTuple()) w.write('L');
		else if (rtt.isPtoPTuple()) w.write('P');
		else if (rtt.isPtoUTuple()) w.write('U');
		else if (rtt.isPtoDETuple()) w.write('E');
		
		w.write(FIELD_DELIM);
		w.write(rtt.getTupleIui().toString());
		w.write(BLOCK_DELIM);
		
		if (rtt.isATuple()) writeATuple(rtt);
		else if (rtt.isMetadataTuple()) writeDTuple(rtt);
		else if (rtt.isPtoCTuple()) writePtoCTuple(rtt);
		else if (rtt.isPtoLackUTuple()) writePtoLackUTuple(rtt);
		else if (rtt.isPtoPTuple()) writePtoPTuple(rtt);
		else if (rtt.isPtoUTuple()) writePtoUTuple(rtt);
		else if (rtt.isPtoDETuple()) writePtoDETuple(rtt);
		
		w.write(TUPLE_DELIM);
	}

	static Iso8601DateTimeFormatter formatter = new Iso8601DateTimeFormatter();
	
	private void writeATuple(RtsTuple rtt) throws IOException {
		ATuple rtta = (ATuple)rtt;
		
		w.write(rtta.getAuthorIui().toString());
		w.write(FIELD_DELIM);
		w.write(formatter.format(rtta.getAuthoringTimestamp()));
		w.write(FIELD_DELIM);
		w.write(rtta.getReferentIui().toString());
	}

	private void writeDTuple(RtsTuple rtt) throws IOException {
		/*
		 * Write metadata Tuple in order of specification.  We already wrote Tuple
		 *  	info block before this method was called.  
		 *  
		 *  No quoting needed.
		 *  
		 *  No need to write Tuple delimiter at end.  That's handled above.
		 */
		MetadataTuple rttd = (MetadataTuple)rtt;
		
		w.write(rttd.getAuthorIui().toString());
		w.write(FIELD_DELIM);
		w.write(formatter.format(rttd.getAuthoringTimestamp()));
		w.write(FIELD_DELIM);
		w.write(rttd.getReferent().toString());
		w.write(FIELD_DELIM);
		w.write(rttd.getChangeType().toString());
		w.write(FIELD_DELIM);
		w.write(rttd.getChangeReason().toString());
		w.write(FIELD_DELIM);
		w.write(rttd.getErrorCode().toString());
		w.write(FIELD_DELIM);
		if (rttd.getReplacementTupleIuis() != null) {
			Set<Iui> iuis = rttd.getReplacementTupleIuis();
			Iterator<Iui> i = iuis.iterator();
			while (i.hasNext()) {
				Iui replRtt = i.next();
				w.write(replRtt.toString());
				if (i.hasNext()) w.write(',');
			}
		}
	}

	private void writePtoCTuple(RtsTuple rtt) throws IOException {
		/*
		 * Write PtoC Tuple in order of specification.  We already wrote Tuple
		 *  	info block before this method was called.  
		 *  
		 *  No quoting needed.
		 *  
		 *  No need to write Tuple delimiter at end.  That's handled above.
		 */
		PtoCTuple rttc = (PtoCTuple)rtt;

		w.write(rttc.getAuthorIui().toString());
		w.write(FIELD_DELIM);
		w.write(rttc.getAuthoringTimeReference().toString());
		w.write(FIELD_DELIM);
		w.write(rttc.getConceptSystemIui().toString());
		w.write(FIELD_DELIM);
		w.write(rttc.getReferentIui().toString());
		w.write(FIELD_DELIM);
		w.write(rttc.getConceptCui().toString());
		w.write(FIELD_DELIM);
		w.write(rttc.getTemporalReference().toString());

	}

	private void writePtoLackUTuple(RtsTuple rtt) throws IOException {
		/*
		 * Write PtoLackU Tuple in order of specification.  We already wrote Tuple
		 *  	info block before this method was called.  
		 *  
		 *  No quoting needed.
		 *  
		 *  No need to write Tuple delimiter at end.  That's handled above.
		 */
		PtoLackUTuple rttl = (PtoLackUTuple)rtt;
		
		w.write(rttl.getAuthorIui().toString());
		w.write(FIELD_DELIM);
		w.write(rttl.getAuthoringTimeReference().toString());
		w.write(FIELD_DELIM);
		w.write('<');
		w.write(rttl.getRelationshipURI().toString());
		w.write('>');
		w.write(FIELD_DELIM);
		w.write(rttl.getRelationshipOntologyIui().toString());
		w.write(FIELD_DELIM);
		w.write(rttl.getReferentIui().toString());
		w.write(FIELD_DELIM);
		w.write('<');
		w.write(rttl.getUniversalUui().toString());
		w.write('>');
		w.write(FIELD_DELIM);
		w.write(rttl.getUniversalOntologyIui().toString());
		w.write(FIELD_DELIM);
		w.write(rttl.getTemporalReference().toString());
	}

	private void writePtoPTuple(RtsTuple rtt) throws IOException {
		// TODO Auto-generated method stub
		PtoPTuple rttp = (PtoPTuple)rtt;
		
		w.write(rttp.getAuthorIui().toString());
		w.write(FIELD_DELIM);
		w.write(rttp.getAuthoringTimeReference().toString());
		w.write(FIELD_DELIM);
		w.write('<');
		if (rttp.getRelationshipPolarity().equals(RelationshipPolarity.NEGATED)) {
			w.write("-!-");
		}
		w.write(rttp.getRelationshipURI().toString());
		w.write('>');
		w.write(FIELD_DELIM);
		w.write(rttp.getRelationshipOntologyIui().toString());
		w.write(FIELD_DELIM);
		Iterator<ParticularReference> iuis = rttp.getAllParticulars().iterator();
		while (iuis.hasNext()) {
			ParticularReference pr = iuis.next();
			if (pr instanceof Iui) {
				Iui iui = (Iui)pr;
				w.write("iui=" + iui.toString());
			} else if (pr instanceof TemporalReference) {
				TemporalReference tr = (TemporalReference)pr;
				w.write("tref=" + tr.toString());
			} else {
				throw new RuntimeException("Bad particular reference.");
			}
			if (iuis.hasNext()) w.write(SUBFIELD_DELIM);
		}
		w.write(FIELD_DELIM);
		w.write(rttp.getTemporalReference().toString());
	}

	private void writePtoUTuple(RtsTuple rtt) throws IOException {
		// TODO Auto-generated method stub
		PtoUTuple rttu = (PtoUTuple)rtt;
		
		w.write(rttu.getAuthorIui().toString());
		w.write(FIELD_DELIM);
		w.write(rttu.getAuthoringTimeReference().toString());
		w.write(FIELD_DELIM);
		w.write('<');
		if (rttu.getRelationshipPolarity().equals(RelationshipPolarity.NEGATED)) {
			w.write("-!-");
		}
		w.write(rttu.getRelationshipURI().toString());
		w.write('>');
		w.write(FIELD_DELIM);
		w.write(rttu.getRelationshipOntologyIui().toString());
		w.write(FIELD_DELIM);
		w.write(rttu.getReferentIui().toString());
		w.write(FIELD_DELIM);
		w.write('<');
		w.write(rttu.getUniversalUui().toString());
		w.write('>');
		w.write(FIELD_DELIM);
		w.write(rttu.getUniversalOntologyIui().toString());
		w.write(FIELD_DELIM);
		w.write(rttu.getTemporalReference().toString());
	}

	private void writePtoDETuple(RtsTuple rtt) throws IOException {
		// TODO Auto-generated method stub
		PtoDETuple rtte = (PtoDETuple)rtt;
		
		w.write(rtte.getAuthorIui().toString());
		w.write(FIELD_DELIM);
		w.write(rtte.getAuthoringTimeReference().toString());
		w.write(FIELD_DELIM);
		w.write('<');
		w.write(rtte.getRelationshipURI().toString());
		w.write('>');
		w.write(FIELD_DELIM);
		w.write(rtte.getRelationshipOntologyIui().toString());
		w.write(FIELD_DELIM);
		ParticularReference pr = rtte.getReferent();
		if (pr instanceof Iui)
			w.write("iui=" + rtte.getReferent().toString());
		else if (pr instanceof TemporalReference) {
			TemporalReference tr = (TemporalReference)pr;
			w.write("tref=" + tr.toString());
		} else
			throw new RuntimeException("Uknown kind of particular reference" + pr.getClass());
		w.write(FIELD_DELIM);
		w.write('<');
		w.write(rtte.getDatatypeUui().toString());
		w.write('>');
		w.write(FIELD_DELIM);
		w.write(rtte.getDatatypeOntologyIui().toString());
		w.write(FIELD_DELIM);
		w.write(rtte.getNamingSystem().toString());
		w.write(FIELD_DELIM);
		/* Not a long term solution.  Right now all data are Java UTF-8 strings.  So this line
		 	of code will break with any kind of data other than UTF-8 encoded String.  On the other hand, 
		 	we really only need to escape String data.  So other types of encoded Strings will also 
		 	require escaping
		 */
		System.out.println(Charset.defaultCharset());
		System.out.println(Charset.defaultCharset().name());
		System.out.println(Charset.defaultCharset().displayName());
		String data = new String(rtte.getData());
		String dataForWriting = escapeData(data);
		w.write(dataForWriting);
	}
	
	public String escapeData(String data) {
		StringBuffer sb = new StringBuffer();
		for (char c : data.toCharArray()) {
			if (c == FIELD_DELIM || c == BLOCK_DELIM 
					|| c == TUPLE_DELIM || c == SUBFIELD_DELIM 
					|| c == QUOTE_OPEN || c == QUOTE_CLOSE
					|| c == ESCAPE ) {
				sb.append(ESCAPE);
			}
			sb.append(c);
		}
		return sb.toString();
	}
	
	public void writeTemporalRegion(TemporalRegion tr) throws IOException {
		if (tr.getCalendarSystemIui() == null) {
			System.err.println(tr.getTemporalReference().toString() + "\t" +
					tr.getTemporalReference().isIso());
		}
		w.write("T");
		w.write(BLOCK_DELIM);
		w.write(tr.getTemporalReference().toString());
		w.write(FIELD_DELIM);
		w.write('<');
		w.write(tr.getTemporalType().toString());
		w.write('>');
		w.write(FIELD_DELIM);
		w.write(tr.getCalendarSystemIui().toString());
		w.write(TUPLE_DELIM);
	}
}
