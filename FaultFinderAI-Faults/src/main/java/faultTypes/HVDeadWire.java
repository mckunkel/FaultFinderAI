package faultTypes;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.tuple.Pair;

public class HVDeadWire extends FaultData {

	public HVDeadWire() {
		this.xRnd = ThreadLocalRandom.current().nextInt(1, 113);
		this.yRnd = ThreadLocalRandom.current().nextInt(1, 7);
		this.faultName = FaultNames.DEADWIRE;
	}

	@Override
	protected Fault getInformation() {
		this.faultyWires = new HashMap<>();
		this.faultyWires.put(this.yRnd, Pair.of(xRnd, xRnd));
		return new Fault(this.getClass().getSimpleName(), this.faultName, this.faultyWires);

	}
}
