package edu.ufl.ctsi.rts.text;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

import edu.uams.dbmi.rts.ParticularReference;
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
import edu.uams.dbmi.util.iso8601.Iso8601DateTimeFormatter;

public class TemplateTextWriter {

	public static char FIELD_DELIM = '|';
	public static char SUBFIELD_DELIM = ',';
	public static char BLOCK_DELIM = '~';
	public static char ESCAPE = '\\';
	public static char TEMPLATE_DELIM = '\n';
	
	protected Writer w;
	
	public TemplateTextWriter(Writer w) {
		this.w = w;
	}
	
	public void writeTemplate(RtsTemplate rtt) throws IOException {
		if (rtt.isATemplate()) w.write('A');
		else if (rtt.isMetadataTemplate()) w.write('D');
		else if (rtt.isPtoCTemplate()) w.write('C');
		else if (rtt.isPtoLackUTemplate()) w.write('L');
		else if (rtt.isPtoPTemplate()) w.write('P');
		else if (rtt.isPtoUTemplate()) w.write('U');
		else if (rtt.isPtoDETemplate()) w.write('E');
		
		w.write(FIELD_DELIM);
		w.write(rtt.getTemplateIui().toString());
		w.write(BLOCK_DELIM);
		
		if (rtt.isATemplate()) writeATemplate(rtt);
		else if (rtt.isMetadataTemplate()) writeDTemplate(rtt);
		else if (rtt.isPtoCTemplate()) writePtoCTemplate(rtt);
		else if (rtt.isPtoLackUTemplate()) writePtoLackUTemplate(rtt);
		else if (rtt.isPtoPTemplate()) writePtoPTemplate(rtt);
		else if (rtt.isPtoUTemplate()) writePtoUTemplate(rtt);
		else if (rtt.isPtoDETemplate()) writePtoDETemplate(rtt);
		
		w.write(TEMPLATE_DELIM);
	}

	static Iso8601DateTimeFormatter formatter = new Iso8601DateTimeFormatter();
	
	private void writeATemplate(RtsTemplate rtt) throws IOException {
		ATemplate rtta = (ATemplate)rtt;
		
		w.write(rtta.getReferentIui().toString());
		w.write(FIELD_DELIM);
		w.write(formatter.format(rtta.getAuthoringTimestamp()));
		w.write(FIELD_DELIM);
		w.write(rtta.getAuthorIui().toString());
	}

	private void writeDTemplate(RtsTemplate rtt) throws IOException {
		/*
		 * Write metadata template in order of specification.  We already wrote template
		 *  	info block before this method was called.  
		 *  
		 *  No quoting needed.
		 *  
		 *  No need to write template delimiter at end.  That's handled above.
		 */
		MetadataTemplate rttd = (MetadataTemplate)rtt;
		
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
		if (rttd.getReplacementTemplateIuis() != null) {
			Set<Iui> iuis = rttd.getReplacementTemplateIuis();
			Iterator<Iui> i = iuis.iterator();
			while (i.hasNext()) {
				Iui replRtt = i.next();
				w.write(replRtt.toString());
				if (i.hasNext()) w.write(',');
			}
		}
	}

	private void writePtoCTemplate(RtsTemplate rtt) throws IOException {
		/*
		 * Write PtoC template in order of specification.  We already wrote template
		 *  	info block before this method was called.  
		 *  
		 *  No quoting needed.
		 *  
		 *  No need to write template delimiter at end.  That's handled above.
		 */
		PtoCTemplate rttc = (PtoCTemplate)rtt;

		w.write(rttc.getAuthorIui().toString());
		w.write(FIELD_DELIM);
		w.write(rttc.getAuthoringTimeReference().getIdentifier());
		w.write(FIELD_DELIM);
		w.write(rttc.getConceptSystemIui().toString());
		w.write(FIELD_DELIM);
		w.write(rttc.getReferentIui().toString());
		w.write(FIELD_DELIM);
		w.write(rttc.getConceptCui().toString());
		w.write(FIELD_DELIM);
		w.write(rttc.getTemporalReference().getIdentifier());

	}

	private void writePtoLackUTemplate(RtsTemplate rtt) throws IOException {
		/*
		 * Write PtoLackU template in order of specification.  We already wrote template
		 *  	info block before this method was called.  
		 *  
		 *  No quoting needed.
		 *  
		 *  No need to write template delimiter at end.  That's handled above.
		 */
		PtoLackUTemplate rttl = (PtoLackUTemplate)rtt;
		
		w.write(rttl.getAuthorIui().toString());
		w.write(FIELD_DELIM);
		w.write(rttl.getAuthoringTimeReference().getIdentifier());
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
		w.write(rttl.getTemporalReference().getIdentifier());
	}

	private void writePtoPTemplate(RtsTemplate rtt) throws IOException {
		// TODO Auto-generated method stub
		PtoPTemplate rttp = (PtoPTemplate)rtt;
		
		w.write(rttp.getAuthorIui().toString());
		w.write(FIELD_DELIM);
		w.write(rttp.getAuthoringTimeReference().getIdentifier());
		w.write(FIELD_DELIM);
		w.write('<');
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
				w.write(iui.toString());
			} else if (pr instanceof TemporalReference) {
				TemporalReference tr = (TemporalReference)pr;
				w.write(tr.getIdentifier());
			} else {
				throw new RuntimeException("Bad particular reference.");
			}
			if (iuis.hasNext()) w.write(SUBFIELD_DELIM);
		}
		w.write(FIELD_DELIM);
		w.write(rttp.getTemporalReference().getIdentifier());
	}

	private void writePtoUTemplate(RtsTemplate rtt) throws IOException {
		// TODO Auto-generated method stub
		PtoUTemplate rttu = (PtoUTemplate)rtt;
		
		w.write(rttu.getAuthorIui().toString());
		w.write(FIELD_DELIM);
		w.write(rttu.getAuthoringTimeReference().getIdentifier());
		w.write(FIELD_DELIM);
		w.write('<');
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
		w.write(rttu.getTemporalReference().getIdentifier());
	}

	private void writePtoDETemplate(RtsTemplate rtt) throws IOException {
		// TODO Auto-generated method stub
		PtoDETemplate rtte = (PtoDETemplate)rtt;
		
		w.write(rtte.getAuthorIui().toString());
		w.write(FIELD_DELIM);
		w.write(rtte.getAuthoringTimeReference().getIdentifier());
		w.write(FIELD_DELIM);
		w.write('<');
		w.write(rtte.getRelationshipURI().toString());
		w.write('>');
		w.write(FIELD_DELIM);
		w.write(rtte.getRelationshipOntologyIui().toString());
		w.write(FIELD_DELIM);
		ParticularReference pr = rtte.getReferent();
		if (pr instanceof Iui)
			w.write(rtte.getReferent().toString());
		else if (pr instanceof TemporalReference) {
			TemporalReference tr = (TemporalReference)pr;
			w.write(tr.getIdentifier());
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
			if (c == FIELD_DELIM || c == BLOCK_DELIM || c == TEMPLATE_DELIM) {
				sb.append(ESCAPE);
			}
			sb.append(c);
		}
		return sb.toString();
	}
	
	public void writeTemporalReference(TemporalReference tr) throws IOException {
		w.write("T");
		w.write(FIELD_DELIM);
		w.write(tr.getIdentifier());
		w.write(FIELD_DELIM);
		w.write('<');
		w.write(tr.getTemporalType().toString());
		w.write('>');
		w.write(FIELD_DELIM);
		w.write(tr.getCalendarSystemIui().toString());
		w.write(TEMPLATE_DELIM);
	}
}
