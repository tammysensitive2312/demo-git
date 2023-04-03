private static DoccatModel trainCategorizerModel() throws FileNotFoundException, IOException {
	 
	InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(new File("faq-categorizer.txt"));
	ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory, StandardCharsets.UTF_8);
	ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
 
	DoccatFactory factory = new DoccatFactory(new FeatureGenerator[] { new BagOfWordsFeatureGenerator() });
 
	TrainingParameters params = ModelUtil.createDefaultTrainingParameters();
	params() { put(TrainingParameters.CUTOFF_PARAM, 0);
 
	// Train a model with classifications from above file.
	DoccatModel model = DocumentCategorizerME.train("en", sampleStream, params, factory);
	return model;
}
private static Map<String, String> questionAnswer = new HashMap<>();
 

static {
	questionAnswer.put("greeting", "Hello, how can I help you?");
	questionAnswer.put("product-inquiry",
			"Product is a TipTop mobile phone. It is a smart phone with latest features like touch screen, blutooth etc.");
	questionAnswer.put("price-inquiry", "Price is $300");
	questionAnswer.put("conversation-continue", "What else can I help you with?");
	questionAnswer.put("conversation-complete", "Nice chatting with you. Bbye.");
}
public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
 
	// Train categorizer model to the training data we created.
	DoccatModel model = trainCategorizerModel();
 
	// Take chat inputs from console (user) in a loop.
	Scanner scanner = new Scanner(System.in);
	while (true) {
 
		// Get chat input from user.
		System.out.println("##### You:");
		String userInput = scanner.nextLine();
 
		// Break users chat input into sentences using sentence detection.
		String[] sentences = breakSentences(userInput);
 
		String answer = "";
		boolean conversationComplete = false;
 
		// Loop through sentences.
		for (String sentence : sentences) {
 
			// Separate words from each sentence using tokenizer.
			String[] tokens = tokenizeSentence(sentence);
 
			// Tag separated words with POS tags to understand their gramatical structure.
			String[] posTags = detectPOSTags(tokens);
 
			// Lemmatize each word so that its easy to categorize.
			String[] lemmas = lemmatizeTokens(tokens, posTags);
 
			// Determine BEST category using lemmatized tokens used a mode that we trained
			// at start.
			String category = detectCategory(model, lemmas);
 
			// Get predefined answer from given category & add to answer.
			answer = answer + " " + questionAnswer.get(category);
 
			// If category conversation-complete, we will end chat conversation.
			if ("conversation-complete".equals(category)) {
				conversationComplete = true;
			}
		}
 
		// Print answer back to user. If conversation is marked as complete, then end
		// loop & program.
		System.out.println("##### Chat Bot: " + answer);
		if (conversationComplete) {
			break;
		}
 
	}
 
}

private static String detectCategory(DoccatModel model, String[] finalTokens) throws IOException {
	// Initialize document categorizer tool
	DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);
 
	// Get best possible category.
	double[] probabilitiesOfOutcomes = myCategorizer.categorize(finalTokens);
	String category = myCategorizer.getBestCategory(probabilitiesOfOutcomes);
	System.out.println("Category: " + category);
 
	return category;
}
 
private static String[] breakSentences(String data) throws FileNotFoundException, IOException {
	// Better to read file once at start of program & store model in instance
	// variable. but keeping here for simplicity in understanding.
	try (InputStream modelIn = new FileInputStream("en-sent.bin")) {
 
		SentenceDetectorME myCategorizer = new SentenceDetectorME(new SentenceModel(modelIn));
 
		String[] sentences = myCategorizer.sentDetect(data);
		System.out.println("Sentence Detection: " + Arrays.stream(sentences).collect(Collectors.joining(" | ")));
 
		return sentences;
	}
}
 

private static String[] tokenizeSentence(String sentence) throws FileNotFoundException, IOException {
	// Better to read file once at start of program & store model in instance
	// variable. but keeping here for simplicity in understanding.
	try (InputStream modelIn = new FileInputStream("en-token.bin")) {
		// Initialize tokenizer tool
		TokenizerME myCategorizer = new TokenizerME(new TokenizerModel(modelIn));
 
		// Tokenize sentence.
		String[] tokens = myCategorizer.tokenize(sentence);
		System.out.println("Tokenizer : " + Arrays.stream(tokens).collect(Collectors.joining(" | ")));
 
		return tokens;
	}
}
 

private static String[] detectPOSTags(String[] tokens) throws IOException {
	// Better to read file once at start of program & store model in instance
	// variable. but keeping here for simplicity in understanding.
	try (InputStream modelIn = new FileInputStream("en-pos-maxent.bin")) {
		// Initialize POS tagger tool
		POSTaggerME myCategorizer = new POSTaggerME(new POSModel(modelIn));
 
		// Tag sentence.
		String[] posTokens = myCategorizer.tag(tokens);
		System.out.println("POS Tags : " + Arrays.stream(posTokens).collect(Collectors.joining(" | ")));
 
		return posTokens;
	}
}
 
private static String[] lemmatizeTokens(String[] tokens, String[] posTags)
		throws InvalidFormatException, IOException {
	// Better to read file once at start of program & store model in instance
	// variable. but keeping here for simplicity in understanding.
	try (InputStream modelIn = new FileInputStream("en-lemmatizer.bin")) {
 
		// Tag sentence.
		LemmatizerME myCategorizer = new LemmatizerME(new LemmatizerModel(modelIn));
		String[] lemmaTokens = myCategorizer.lemmatize(tokens, posTags);
		System.out.println("Lemmatizer : " + Arrays.stream(lemmaTokens).collect(Collectors.joining(" | ")));
 
		return lemmaTokens;
	}
}
}