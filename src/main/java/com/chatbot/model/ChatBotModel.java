package com.chatbot.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

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

	InputStream inputStream = ChatBotModel.class.getResourceAsStream("/faq-categorizer.txt");
	File f = new File("./src/main/resources/faq-categorizer.txt");
	FileUtils.copyInputStreamToFile(inputStream, f);
	InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(f);
	ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory, StandardCharsets.UTF_8);
	ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

	DoccatFactory doccatFactory = new DoccatFactory(new FeatureGenerator[] { new BagOfWordsFeatureGenerator() });

	TrainingParameters trainingParams = ModelUtil.createDefaultTrainingParameters();
	trainingParams.put(TrainingParameters.CUTOFF_PARAM, 2);

	DoccatModel doccatModel = DocumentCategorizerME.train("en", sampleStream, trainingParams, doccatFactory);
	return doccatModel;
    }

    public static String[] breakSentences(String data) throws FileNotFoundException, IOException {
	try (InputStream model = ChatBotModel.class.getResourceAsStream("/en-sent.bin")) {

	    SentenceDetectorME categorizer = new SentenceDetectorME(new SentenceModel(model));

	    String[] sentenceArray = categorizer.sentDetect(data);
	    System.out.println("Sentence Detection: " + Arrays.stream(sentenceArray).collect(Collectors.joining(" | ")));

	    return sentenceArray;
	}
    }

    public static String[] tokenizeSentence(String sentence) throws FileNotFoundException, IOException {
	try (InputStream model = ChatBotModel.class.getResourceAsStream("/en-token.bin")) {
	    TokenizerME categorizer = new TokenizerME(new TokenizerModel(model));

	    String[] tokenArray = categorizer.tokenize(sentence);
	    System.out.println("Tokenizer : " + Arrays.stream(tokenArray).collect(Collectors.joining(" | ")));

	    return tokenArray;
	}
    }

    public static String[] detectPOSTags(String[] tokens) throws IOException {
	try (InputStream model = ChatBotModel.class.getResourceAsStream("/en-pos-maxent.bin")) {
	    POSTaggerME categorizer = new POSTaggerME(new POSModel(model));

	    String[] posTokenArray = categorizer.tag(tokens);
	    System.out.println("POS Tags : " + Arrays.stream(posTokenArray).collect(Collectors.joining(" | ")));

	    return posTokenArray;
	}
    }

    public static String[] lemmatizeTokens(String[] tokens, String[] posTags)
	    throws InvalidFormatException, IOException {
	try (InputStream model = ChatBotModel.class.getResourceAsStream("/en-lemmatizer.dict")) {

	    DictionaryLemmatizer lemmatizer = new DictionaryLemmatizer(model);
	    String[] lemmaTokenArray = lemmatizer.lemmatize(tokens, posTags);
	    System.out.println("Lemmatizer : " + Arrays.stream(lemmaTokenArray).collect(Collectors.joining(" | ")));

	    return lemmaTokenArray;
	}
    }

    public static String detectCategory(DoccatModel model, String[] finalTokens) throws IOException {
	DocumentCategorizerME categorizer = new DocumentCategorizerME(model);

	double[] probabilitiesOfOutcomesArray = categorizer.categorize(finalTokens);
	String category = categorizer.getBestCategory(probabilitiesOfOutcomesArray);
	System.out.println("Category: " + category);

	return category;
    }
}
