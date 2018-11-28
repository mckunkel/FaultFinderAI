/**
 * 
 */
package clasDC.objects;

import java.util.List;

import clasDC.faults.FaultNames;
import lombok.Builder;

/**
 * @author m.c.kunkel
 *
 */
public class DCSystem extends CLASObject {
	@Builder
	private DCSystem(int nchannels, int maxFaults, List<FaultNames> desiredFaults, boolean singleFaultGen) {
		if (!(nchannels == 3 || nchannels == 1)) {
			throw new IllegalArgumentException(
					"Invalid input: (nchannels), must have values of" + " 3 or 1. Received: (" + nchannels + ")");
		}
		this.nchannels = nchannels;
		this.maxFaults = maxFaults;
		this.desiredFaults = desiredFaults;
		this.singleFaultGen = singleFaultGen;

		this.objectType = "DCSystem";
		this.height = 72 * 3;
		setPriors();

	}

}
