package edu.uams.dbmi.rts.persist.demofilestore;

import java.net.URI;
import java.net.URISyntaxException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.uams.dbmi.rts.ParticularReference;
import edu.uams.dbmi.rts.RtsDeclaration;
import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.persist.RtsStore;
import edu.uams.dbmi.rts.query.TupleQuery;
import edu.uams.dbmi.rts.time.TemporalRegion;
import edu.uams.dbmi.rts.tuple.PtoDETuple;
import edu.uams.dbmi.rts.tuple.PtoPTuple;
import edu.uams.dbmi.rts.tuple.RtsTuple;
import edu.uams.dbmi.rts.tuple.RtsTupleType;
import edu.uams.dbmi.rts.uui.Uui;

import edu.ufl.ctsi.rts.text.*;

public class DemoFileStore implements RtsStore {
	
	File dir;
	HashMap<RtsTupleType, RtsTupleTextWriter> writers;
	HashMap<RtsTupleType, RtsTupleTextParser> readers;
	RtsTupleTextWriter tempRegionWriter;
	RtsTupleTextParser tempRegionReader;
	
	HashSet<RtsDeclaration> cache;

	public DemoFileStore() {
		this(new File("./"));
	}
	
	public DemoFileStore(File directory) {
		this.dir = directory;
		if (!this.dir.exists()) {
			System.out.println("Making directory: " + dir.toString());
			this.dir.mkdir();
		}
		cache = new HashSet<RtsDeclaration>();
		writers = new HashMap<RtsTupleType, RtsTupleTextWriter>();
		readers = new HashMap<RtsTupleType, RtsTupleTextParser>();
	}

	@Override
	public boolean saveTuple(RtsTuple Tuple) {
		return saveRtsDeclaration(Tuple);
	}

	@Override
	public RtsTuple getTuple(Iui iui) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<RtsTuple> getByReferentIui(Iui iui) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<RtsTuple> getByAuthorIui(Iui iui) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iui getAvailableIui() {
		return Iui.createRandomIui();
	}

	@Override
	public Set<RtsTuple> runQuery(TupleQuery query) {
		try {
			setupReaders();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashSet<RtsTuple> queryResult = new HashSet<RtsTuple>();
		Iterator<RtsTupleType> types = query.getTypes();
		while (types.hasNext()) {
			RtsTupleTextParser rttp = readers.get(types.next());
			matchTuplesWithType(rttp, query, queryResult);
		}
		return queryResult;
	}

	private void setupReaders() throws IOException {
		for (RtsTupleType rtt : RtsTupleType.values()) {
			File rttf = new File(dir, rtt.toString()+".txt");
			if (!rttf.exists()) rttf.createNewFile();
			FileReader fr = new FileReader(rttf);
			BufferedReader br = new BufferedReader(fr);
			RtsTupleTextParser rttw = new RtsTupleTextParser(br);
			readers.put(rtt, rttw);
		}
		//File rttf = new File(dir, "T.txt");
		//FileWriter fw = new FileWriter(rttf, true);
		//tempRegionWriter = new RtsTupleTextWriter(fw);
		
	}

	private void matchTuplesWithType(RtsTupleTextParser rttp, TupleQuery query, HashSet<RtsTuple> queryResult) {
		// TODO Auto-generated method stub
		try {
			rttp.parseTuples();
			Iterator<RtsTuple> t = rttp.iterator();
			while (t.hasNext()) {
				RtsTuple rt = t.next();
				if (query.matches(rt)) {
					queryResult.add(rt);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void shutDown() {
		closeWriters();
		//closeReaders();
	}

	private void closeWriters() {
		Set<RtsTupleType> keys = writers.keySet();
		for (RtsTupleType rtt : keys) {
			RtsTupleTextWriter rttw = writers.get(rtt);
			try {
				rttw.close();
			} catch (IOException e) {
				System.err.println("Error closing writer " + rttw);
				e.printStackTrace();
			}
		}
		try {
			tempRegionWriter.close();
		} catch (IOException ioe) {
			System.err.println("Error closing temporal region writer.");
			ioe.printStackTrace();
		}
	}

	@Override
	public void commit() {
		try {
			openWriters();
		} catch (IOException e) {
			System.err.println("This is the commit() function. There was an error while trying to open the writers.");
			e.printStackTrace();
		}
		try {
			writeCache();
		} catch (IOException e) {
			System.err.println("This is the commit() function. There was an error while trying to write the cache to disk.");
			e.printStackTrace();
		}
		closeWriters();
		cache.clear();
	}

	private void writeCache() throws IOException {
		// TODO Auto-generated method stub
		for (RtsDeclaration rd: cache) {
			if (rd instanceof TemporalRegion) {
				tempRegionWriter.writeTemporalRegion((TemporalRegion)rd);
			} else {
				RtsTuple rt = (RtsTuple)rd;
				RtsTupleType rtt = rt.getRtsTupleType();
				RtsTupleTextWriter rttw = writers.get(rtt);
				rttw.writeTuple(rt);
			}
		}
	}

	private void openWriters() throws IOException {
		for (RtsTupleType rtt : RtsTupleType.values()) {
			File rttf = new File(dir, rtt.toString()+".txt");
			System.err.println(rttf);
			if (!rttf.exists()) rttf.createNewFile();
			FileWriter fw = new FileWriter(rttf, true);
			RtsTupleTextWriter rttw = new RtsTupleTextWriter(fw);
			writers.put(rtt, rttw);
		}
		File rttf = new File(dir, "T.txt");
		FileWriter fw = new FileWriter(rttf, true);
		tempRegionWriter = new RtsTupleTextWriter(fw);
	}

	@Override
	public boolean saveRtsDeclaration(RtsDeclaration rd) {
		boolean success=false;
		try {
			cache.add(rd);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return success;
	}

	@Override
	public Set<ParticularReference> getReferentsByTypeAndDesignatorType(Uui referentType, Uui designatorType, String designatorTxt) {
		//HashSet<Iui> foundIuis = new HashSet<Iui>();
		Set<Iui> designatorIuis = getDesignatorsByExactMatch(designatorTxt);
		Set<Iui> dIuisOfType = filterIuisByType(designatorIuis, referentType);
		return getDenotesTargetsOfIuis(dIuisOfType);
	}

	protected Set<Iui> getDesignatorsByExactMatch(String text) {
		TupleQuery tq = new TupleQuery();
		tq.addType(RtsTupleType.PTODETUPLE);
		tq.setData(text.getBytes());
		Set<RtsTuple> rts = this.runQuery(tq);
		HashSet<Iui> dIuis = new HashSet<Iui>();
		for (RtsTuple rt : rts) {
			PtoDETuple de = (PtoDETuple)rt;
			dIuis.add((Iui)de.getReferent());
		}
		return dIuis;
	}

	protected Set<Iui> filterIuisByType(Set<Iui> iuis, Uui type) {
		HashSet<Iui> filteredIuis = new HashSet<Iui>();
		try {
			URI instanceOfUri = new URI("http://purl.obolibrary.org/obo/OBO_REL#_instance_of");
			for (Iui i : iuis) {
				TupleQuery tq = new TupleQuery();
				tq.addType(RtsTupleType.PTOUTUPLE);
				tq.setReferentIui(i);
				tq.setUniversalUui(type);
				tq.setRelationshipURI(instanceOfUri);
				Set<RtsTuple> rts = this.runQuery(tq);
				if (rts.size() > 0) filteredIuis.add(i);
			}		
		} catch (URISyntaxException use) {
			use.printStackTrace();
		}
		return filteredIuis;
	}

	protected Set<ParticularReference> getDenotesTargetsOfIuis(Set<Iui> dIuis) {
		HashSet<ParticularReference> targetReferences = new HashSet<ParticularReference>();
		for (Iui i : dIuis) {
			TupleQuery tq = new TupleQuery();
			tq.addType(RtsTupleType.PTOPTUPLE);
			ArrayList<ParticularReference> iuiList = new ArrayList<ParticularReference>();
			iuiList.add(i);
			tq.setP(iuiList);
			try {
				URI denotesUri = new URI("http://purl.obolibrary.org/obo/IAO_0000219");
				tq.setRelationshipURI(denotesUri);
				Set<RtsTuple> rts = this.runQuery(tq);
				for (RtsTuple rt : rts) {
					PtoPTuple ptop = (PtoPTuple)rt;
					List<ParticularReference> p = ptop.getAllParticulars();
					Iui first = (Iui)p.get(0);
					if (i.equals(first)) {
						targetReferences.add(p.get(1));
					}
				}
			} catch (URISyntaxException use) {
				use.printStackTrace();
			}
		}
		return targetReferences;
	}
}
