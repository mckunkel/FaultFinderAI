package client;

import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.SubsamplingLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.activations.impl.ActivationReLU;
import org.nd4j.linalg.activations.impl.ActivationSoftmax;
import org.nd4j.linalg.learning.config.AdaDelta;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.impl.LossNegativeLogLikelihood;

/**
 * This class is used to retrieve all the different models that are available.
 */
public class ModelFactory {

	public static MultiLayerNetwork simpleCNN(int numLabels) {

		// create the network configuration
		MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder()
				// user xavier initialization
				.weightInit(WeightInit.XAVIER).optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
				.updater(new AdaDelta()).list()
				// the first layer is a convolution layer with a kernel 2px high
				// and 3px wide
				.layer(0,
						new ConvolutionLayer.Builder(3, 2)
								// use one input channel
								.nIn(1).stride(1, 1).nOut(20).activation(new ActivationReLU()).build())
				// next use a pooling (subsampling) layer utilizing MAX-pooling
				.layer(1,
						new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX).kernelSize(2, 1).stride(1, 1)
								.build())
				// hidden layer in the densely connected network
				.layer(2,
						new DenseLayer.Builder().activation(new ActivationReLU())
								// number of hidden neurons
								.nOut(100).build())
				// output layer of the network using negativeloglikelihood as
				// loss function
				.layer(3,
						new OutputLayer.Builder(new LossNegativeLogLikelihood())
								// use as many output neurons as there are
								// labels
								.nOut(numLabels).activation(new ActivationSoftmax()).build())
				// the images are represented as vectors, thus the input type is
				// convolutionalFlat
				.setInputType(InputType.convolutionalFlat(112, 6, 1)).backprop(true).pretrain(false).build();

		// now create the neural network from the configuration
		MultiLayerNetwork neuralNetwork = new MultiLayerNetwork(configuration);
		// initialize the network
		neuralNetwork.init();

		return neuralNetwork;
	}

	public static MultiLayerNetwork simpleNN(int numInputs, int numLabels) {
		int numHiddenNodes = numInputs / 2;
		MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder().weightInit(WeightInit.XAVIER)
				.optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT).updater(new AdaDelta()).list()
				.layer(0,
						new OutputLayer.Builder(new LossNegativeLogLikelihood())
								// use as many output neurons as there are
								// labels
								.nIn(numInputs).nOut(numLabels).weightInit(WeightInit.XAVIER)
								.activation(new ActivationSoftmax()).build())
				.setInputType(InputType.convolutionalFlat(112, 6, 1)).pretrain(false).backprop(true).build();
		// now create the neural network from the configuration
		MultiLayerNetwork neuralNetwork = new MultiLayerNetwork(configuration);
		// initialize the network
		neuralNetwork.init();

		return neuralNetwork;
	}

	public static MultiLayerNetwork simpleDNNOne(int numInputs, int numLabels) {
		int numHiddenNodes = numInputs / 2;
		MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder().weightInit(WeightInit.XAVIER)
				.optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT).updater(new AdaDelta()).list()
				.layer(0,
						new DenseLayer.Builder().nIn(numInputs).nOut(numLabels).weightInit(WeightInit.XAVIER)
								.activation(Activation.RELU).build())
				.layer(1,
						new OutputLayer.Builder(new LossNegativeLogLikelihood())
								// use as many output neurons as there are
								// labels
								.nIn(numLabels).nOut(numLabels).activation(new ActivationSoftmax()).build())
				.setInputType(InputType.convolutionalFlat(112, 6, 1)).pretrain(false).backprop(true).build();
		// now create the neural network from the configuration
		MultiLayerNetwork neuralNetwork = new MultiLayerNetwork(configuration);
		// initialize the network
		neuralNetwork.init();

		return neuralNetwork;
	}

	public static MultiLayerNetwork simpleDNN(int numInputs, int numLabels) {
		int numHiddenNodes = numInputs / 2;
		MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder().weightInit(WeightInit.XAVIER)
				.optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT).updater(new AdaDelta()).list()
				.layer(0,
						new DenseLayer.Builder().nIn(numInputs).nOut(numHiddenNodes).weightInit(WeightInit.XAVIER)
								.activation(Activation.RELU).build())
				.layer(1,
						new DenseLayer.Builder().nIn(numHiddenNodes).nOut(numHiddenNodes / 2)
								.weightInit(WeightInit.XAVIER).activation(Activation.RELU).build())
				.layer(2,
						new OutputLayer.Builder(new LossNegativeLogLikelihood())
								// use as many output neurons as there are
								// labels
								.nOut(numLabels).activation(new ActivationSoftmax()).build())
				.setInputType(InputType.feedForward(numInputs)).pretrain(false).backprop(true).build();
		// now create the neural network from the configuration
		MultiLayerNetwork neuralNetwork = new MultiLayerNetwork(configuration);
		// initialize the network
		neuralNetwork.init();

		return neuralNetwork;
	}

	public static MultiLayerNetwork simpleDNNII(int numInputs, int numLabels) {
		int numHiddenNodes = numInputs / 2;
		MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder().weightInit(WeightInit.XAVIER)
				.optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT).updater(new AdaDelta()).list()
				.layer(0,
						new DenseLayer.Builder().nIn(numInputs).nOut(numHiddenNodes).weightInit(WeightInit.XAVIER)
								.activation(Activation.RELU).build())
				.layer(1,
						new DenseLayer.Builder().nIn(numHiddenNodes).nOut(numHiddenNodes / 2)
								.weightInit(WeightInit.XAVIER).activation(Activation.RELU).build())
				.layer(2,
						new OutputLayer.Builder(new LossNegativeLogLikelihood())
								// use as many output neurons as there are
								// labels
								.nOut(numLabels).activation(new ActivationSoftmax()).build())
				.setInputType(InputType.convolutionalFlat(112, 6, 1)).pretrain(false).backprop(true).build();
		// now create the neural network from the configuration
		MultiLayerNetwork neuralNetwork = new MultiLayerNetwork(configuration);
		// initialize the network
		neuralNetwork.init();

		return neuralNetwork;
	}

	public static MultiLayerNetwork anomolyDetection(int numInputs, int numLabels) {

		// Set up network. 672 in/out (as DC data is arranged as 112x6).
		// 672 -> 225 -> 10 -> 225 -> 672

		MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder().weightInit(WeightInit.XAVIER)
				.optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT).updater(new AdaDelta()).list()
				.layer(0,
						new DenseLayer.Builder().nIn(numInputs).nOut(225).weightInit(WeightInit.XAVIER)
								.activation(Activation.RELU).build())
				.layer(1,
						new DenseLayer.Builder().nIn(225).nOut(10).weightInit(WeightInit.XAVIER)
								.activation(Activation.RELU).build())
				.layer(2,
						new DenseLayer.Builder().nIn(10).nOut(225).weightInit(WeightInit.XAVIER)
								.activation(Activation.RELU).build())
				.layer(3,
						new OutputLayer.Builder(new LossNegativeLogLikelihood())
								// use as many output neurons as there are
								// labels
								.nIn(225).nOut(numLabels).activation(new ActivationSoftmax()).build())
				.setInputType(InputType.convolutionalFlat(112, 6, 1)).pretrain(false).backprop(true).build();

		// now create the neural network from the configuration
		MultiLayerNetwork neuralNetwork = new MultiLayerNetwork(configuration);
		// initialize the network
		neuralNetwork.init();

		return neuralNetwork;
	}

	public static MultiLayerNetwork mnistSetUp(int numInputs, int numLabels) {
		int rngSeed = 123; // random number seed for reproducibility
		double rate = 0.0015; // learning rate

		MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder().seed(rngSeed) // include
				// a
				// random
				// seed
				// for
				// reproducibility
				.activation(Activation.RELU).weightInit(WeightInit.XAVIER).updater(new Nesterovs(rate, 0.98))
				.l2(rate * 0.005) // regularize learning model
				.list()
				.layer(0,
						new DenseLayer.Builder() // create the first input
													// layer.
								.nIn(numInputs).nOut(500).build())
				.layer(1,
						new DenseLayer.Builder() // create the second input
													// layer
								.nIn(500).nOut(100).build())
				.layer(2,
						new OutputLayer.Builder(new LossNegativeLogLikelihood()).activation(new ActivationSoftmax())
								.nIn(100).nOut(numLabels).build())
				.pretrain(false).backprop(true) // use backpropagation to adjust
												// weights
				.build();

		// now create the neural network from the configuration
		MultiLayerNetwork neuralNetwork = new MultiLayerNetwork(configuration);
		// initialize the network
		neuralNetwork.init();

		return neuralNetwork;
	}
}
