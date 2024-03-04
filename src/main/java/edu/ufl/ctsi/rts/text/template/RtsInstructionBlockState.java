package edu.ufl.ctsi.rts.text.template;

public class RtsInstructionBlockState {

	int blockNumber;
	boolean executed;

	public RtsInstructionBlockState(int blockNumber) {
		this.blockNumber = blockNumber;
		executed = false;
	}

	public void markAsExecuted() {
		executed = true;
	}

	public void markAsNotExecuted() {
		executed = false;
	}

	public boolean isExecuted() {
		return executed;
	}

	public int getBlockNumber() {
		return blockNumber;
	}
}