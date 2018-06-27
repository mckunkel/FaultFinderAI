package faulttypes;

import org.jlab.groot.data.H2F;
import org.jlab.groot.ui.TCanvas;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.util.ArrayUtil;
import org.nd4j.linalg.util.NDArrayUtil;

import utils.ArrayUtilities;
import utils.FaultUtils;

public abstract class FaultData {

	protected int nLayers = ArrayUtilities.nLayers;
	protected int rangeMax = FaultUtils.RANGE_MAX;
	protected int rangeMin = FaultUtils.RANGE_MIN;

	protected int faultRangeMax = FaultUtils.FAULT_RANGE_MAX;
	protected int faultRangeMin = FaultUtils.FAULT_RANGE_MIN;

	protected int xRnd;
	protected int yRnd;
	protected int faultLocation;
	protected int[][] data = new int[112][6];
	protected int[] label;
	protected int[] reducedLabel;

	protected int[][] getData() {
		return data;
	}

	protected int[] getLabel() {
		return label;
	}

	protected int[] getReducedLabel() {
		return reducedLabel;
	}

	public int getXRand() {
		return xRnd;
	}

	public int getYRand() {
		return yRnd;
	}

	public int getFaultLocation() {
		return faultLocation;
	};

	protected abstract void makeDataSet();

	public void plotData() {
		TCanvas canvas = new TCanvas("Training Data", 800, 1200);
		H2F hData = new H2F("Training Data", 112, 1, 112, 6, 1, 6);
		for (int i = 0; i < data[0].length; i++) { // i are the rows (layers)
			for (int j = 0; j < data.length; j++) { // j are the columns (wires)
				hData.setBinContent(j, i, data[j][i]);
			}
		}
		canvas.draw(hData);

	}

	public INDArray getFeatureVector() {
		return NDArrayUtil.toNDArray(ArrayUtil.flatten(data));
	}
}
