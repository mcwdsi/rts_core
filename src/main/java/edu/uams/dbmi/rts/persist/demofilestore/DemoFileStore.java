package edu.uams.dbmi.rts.persist.demofilestore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import edu.uams.dbmi.rts.RtsDeclaration;
import edu.uams.dbmi.rts.iui.Iui;
import edu.uams.dbmi.rts.persist.RtsStore;
import edu.uams.dbmi.rts.query.TupleQuery;
import edu.uams.dbmi.rts.time.TemporalRegion;
import edu.uams.dbmi.rts.tuple.RtsTuple;
import edu.uams.dbmi.rts.tuple.RtsTupleType;

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
}
