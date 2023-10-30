package com.chatbot.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import opennlp.tools.doccat.BagOfWordsFeatureGenerator;
import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.doccat.FeatureGenerator;
import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;
import opennlp.tools.util.model.ModelUtil;

public class ChatBotModel {

    public static DoccatModel trainCategorizerModel() throws FileNotFoundException, IOException {
	// faq-categorizer.txt is a custom training data with categories as per our chat
	// requirements.
	InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(
		new File("./src/main/resources/faq-categorizer.txt"));
	ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory, StandardCharsets.UTF_8);
	ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

	DoccatFactory factory = new DoccatFactory(new FeatureGenerator[] { new BagOfWordsFeatureGenerator() });

	TrainingParameters params = ModelUtil.createDefaultTrainingParameters();
	params.put(TrainingParameters.CUTOFF_PARAM, 2);

	// Train a model with classifications from above file.
	DoccatModel model = DocumentCategorizerME.train("en", sampleStream, params, factory);
	return model;
    }

    public static String[] breakSentences(String data) throws FileNotFoundException, IOException {
	// Better to read file once at start of program & store model in instance
	// variable. but keeping here for simplicity in understanding.
	try (InputStream modelIn = new FileInputStream("./src/main/resources/en-sent.bin")) {

	    SentenceDetectorME myCategorizer = new SentenceDetectorME(new SentenceModel(modelIn));

	    String[] sentences = myCategorizer.sentDetect(data);
	    System.out.println("Sentence Detection: " + Arrays.stream(sentences).collect(Collectors.joining(" | ")));

	    return sentences;
	}
    }

    public static String[] tokenizeSentence(String sentence) throws FileNotFoundException, IOException {
	// Better to read file once at start of program & store model in instance
	// variable. but keeping here for simplicity in understanding.
	try (InputStream modelIn = new FileInputStream("./src/main/resources/en-token.bin")) {
	    // Initialize tokenizer tool
	    TokenizerME myCategorizer = new TokenizerME(new TokenizerModel(modelIn));

	    // Tokenize sentence.
	    String[] tokens = myCategorizer.tokenize(sentence);
	    System.out.println("Tokenizer : " + Arrays.stream(tokens).collect(Collectors.joining(" | ")));

	    return tokens;
	}
    }

    public static String[] detectPOSTags(String[] tokens) throws IOException {
	// Better to read file once at start of program & store model in instance
	// variable. but keeping here for simplicity in understanding.
	try (InputStream modelIn = new FileInputStream("./src/main/resources/en-pos-maxent.bin")) {
	    // Initialize POS tagger tool
	    POSTaggerME myCategorizer = new POSTaggerME(new POSModel(modelIn));

	    // Tag sentence.
	    String[] posTokens = myCategorizer.tag(tokens);
	    System.out.println("POS Tags : " + Arrays.stream(posTokens).collect(Collectors.joining(" | ")));

	    return posTokens;
	}
    }

    public static String[] lemmatizeTokens(String[] tokens, String[] posTags)
	    throws InvalidFormatException, IOException {
	// Better to read file once at start of program & store model in instance
	// variable. but keeping here for simplicity in understanding.
	try (InputStream modelIn = new FileInputStream("./src/main/resources/en-lemmatizer.dict")) {

	    // Tag sentence.
//	    LemmatizerME myCategorizer = new LemmatizerME(new LemmatizerModel(modelIn));
	    DictionaryLemmatizer lemmatizer = new DictionaryLemmatizer(modelIn);
	    String[] lemmaTokens = lemmatizer.lemmatize(tokens, posTags);
	    System.out.println("Lemmatizer : " + Arrays.stream(lemmaTokens).collect(Collectors.joining(" | ")));

	    return lemmaTokens;
	}
    }

    public static String detectCategory(DoccatModel model, String[] finalTokens) throws IOException {
	// Initialize document categorizer tool
	DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);

	// Get best possible category.
	double[] probabilitiesOfOutcomes = myCategorizer.categorize(finalTokens);
	String category = myCategorizer.getBestCategory(probabilitiesOfOutcomes);
	System.out.println("Category: " + category);

	return category;
    }
}
