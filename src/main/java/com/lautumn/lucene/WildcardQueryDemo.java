package com.lautumn.lucene;

import com.lautumn.lucene.bean.Person;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.search.uhighlight.UnifiedHighlighter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/28.
 */
public class WildcardQueryDemo {
    private static final Logger logger = LoggerFactory.getLogger(WildcardQueryDemo.class);
    private static final String INDEX_PATH = "D:\\projects\\lautumn-test\\index";

    /**
     * 获取IndexSearcher
     *
     * @return
     */
    private IndexSearcher getIndexSearcher() {
        try {
            //获得索引库的路径
            Directory dir = FSDirectory.open(Paths.get(INDEX_PATH));
            //获取索引库的读取对象Reader
            DirectoryReader reader = DirectoryReader.open(dir);
            //创建索引库的搜索对象
            return new IndexSearcher(reader);
        } catch (IOException e) {
            logger.error("error while getting indexSearcher... ", e);
            throw new RuntimeException("error while getting indexWriter");
        }
    }

    /**
     * 获取IndexWriter
     *
     * @return
     */
    private IndexWriter getIndexWriter() {
        try {
            //获得索引库的路径
            Directory dir = FSDirectory.open(Paths.get(INDEX_PATH));
            //创建分词器
            Analyzer al = new IKAnalyzer();
            //创建索引的写入的配置对象
            IndexWriterConfig iwc = new IndexWriterConfig(al);
            //创建索引的写入器(Writer)
            return new IndexWriter(dir, iwc);
        } catch (IOException e) {
            logger.error("error while getting indexWriter... ", e);
            throw new RuntimeException("error while getting indexWriter");
        }
    }

    /**
     * 将personList存入index
     */
    public void personListToIndex() {
        IndexWriter indexWriter = null;
        try {
            logger.info("adding work case info into indexDB...");
            indexWriter = getIndexWriter();
            //清除所有数据
            logger.info("deleting all...");
            indexWriter.deleteAll();
            //获取personList
            List<Person> personList = getPersonList(14);
            //存index
            for (Person person : personList) {
                Document doc = new Document();
                doc.add(new TextField("name", person.getName(), Field.Store.YES));
                doc.add(new TextField("info", person.getInfo(), Field.Store.YES));
                doc.add(new LongPoint("age", person.getAge()));
                doc.add(new StoredField("age", person.getAge()));
                indexWriter.addDocument(doc);
                indexWriter.commit();
            }
            logger.info("adding work case info into indexDB finished...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (indexWriter != null) {
                    indexWriter.close();
                }
            } catch (IOException e) {
                logger.error("error while closing indexWriter...", e);
                throw new RuntimeException("error while closing indexWriter...");
            }
        }
    }

    public List<Person> getPersonList(int n) {
        List<Person> persons = new ArrayList<Person>();
        String[][] personString = initPersonString(n);
        for (String[] strings : personString) {
            Person p = new Person(strings[0], Integer.parseInt(strings[1]), strings[2]);
            persons.add(p);
        }
        return persons;
    }

    /**
     * 获取初始化的数据
     * @param N
     * @return
     */
    public String[][] initPersonString(int N) {
        String[][] all = new String[][]{
                {"高秋霞", "20", "怀柔区怀北镇河防口村76号宝德强科技（北京）有限公司The site provides an introduction to understand the basics of and working with the Excel for performing basic statistical computation and its output managerial interpretation."},
                {"于江华", "10", "怀柔区怀北镇河防口村32号北京康特电子股份有限公司 statistical analysis has changed the way people manage their information. Their power and ease of use have given new opportunities for data analysis, but they have also brought new problems and challenges for the user"},
                {"彭兴振", "50", "怀柔区东关二区24-5-101北京信天嘉华科技发展有限公司 18, The Office for National Statistics (ONS) is the executive arm of the UK Statistics Authority, a non-ministerial department which reports directly to Parliament. ONS is the UK government's single largest statistical producer. It compiles information about the UK's society and economy, and provides the evidence-base for ..."},
                {"缐英杰", "12", "怀柔区南华园三区9-4-402华北高速公路有限公司Excel keeps track of a wide range of statistics about your workbooks. These statistics include such mundane and obvious items as the file name, directory, and title. But you can also find out who last worked on the workbook, what keywords are associated with the workbook, and the total editing time spent ..."},
                {"廖荷湘", "3", "怀柔区府前街15号北京永强饭店有限公司 2016年4月21日 - total value of FHTB payments is provided by the FHTB team at Defence. Business Services (DBS) Military Personnel who administer the scheme on the second working day of each month to People-Accommodation (for the preceding month). This information is collated on an excel spreadsheet by desk."},
                {"石德坤", "5", "怀柔区府前街15号怀柔区人民法院 Which Office program are you using? Word. Excel. PowerPoint. Outlook. Word. By using Office, you can quickly assemble, display, and share your data with other people, and take steps to protect your privacy, too. Do any of the following: Specify the personal information that appears in all your Office documents. To help ..."},
                {"邢宇馨", "35", "怀柔区东关一区甲2-3-401北京鑫中建建筑有限公司 2014年10月8日 - The HSCIC will be changing future publication dates for the four compendia reports which cover smoking, alcohol, drugs and obesity.£ The new dates for these reports will be approximately: - Smoking - will move from end August to end May.- Alcohol - will move from end May to end June.- Drugs - will move ..."},
                {"蒋常宏", "18", "怀柔区后横街13-4-103中航四维（北京）航空遥感技术有限公司 6 天前 - Data can be exported into statistical software such as Excel and SAS. more. ... HINTS, from the National Cancer Institute, measures how people access and use health information; how people use information technology to ... Supplies data files for use with statistical software, such as SAS, SPSS, and Stata."},
                {"赫革兰", "21", "怀柔区 桥梓镇怀柔区宝山镇中心小学Microsoft Excel is a spreadsheet developed by Microsoft for Windows, macOS, Android and iOS. It features calculation, graphing tools, pivot tables, and a macro programming language called Visual Basic for Applications. It has been a very widely applied spreadsheet for these platforms, especially since version 5 in 1993, ..."},
                {"王伟", "3", "怀柔区北房镇南房村297号中兵光电科技股份有限公司  Makes it easy to find information about people related to this subject. Displays files that are stored on this particular topic page. These can be any types of files that SharePoint accepts, for example doc, xls, pdf, and wmi files. mation regarding a given subject? It is not only faster; it is the correct version! No more hassle with ..."},
                {"房青松", "23", "怀柔区北房镇南房村474号北京和利时系统工程有限公司It's contributed to the scientific community by the people behind JASP. ... XLSTAT -- an Excel add-in for PC and MAC that holds more than 200 statistical features including data visualization, multivariate data analysis, modeling, .... WinIDAMS -- from UNESCO -- for numerical information processing and statistical analysis."},
                {"孙雷", "28", "怀柔区北房镇宰相庄村765号北京市温榆河双语实验学校Historical data. Information. 2017 revision of benchmark population Using the latest results of the Population Census as the base, the benchmark population for ... a-1, Major items (Labour force, Employed person, Employee, Unemployed person, Not in labour force, Unemployment rate) (Excel:403KB) (since Jan.1953)."},
                {"温跃华", "32", "怀柔区雁栖镇雁栖大街7号8门怀柔区职业学校 2015年3月12日 - Leveraging business intelligence and location information is more important than ever. ... If your spreadsheet data contains X/Y values or Latitude/Longitude, FME automatically converts the rows to geometry. ... You can also create subsets of the large spreadsheet by leveraging pivots and statistics."},
                {"陈利顺", "53", "怀柔区九渡河镇花木村北京宽沟雁栖山庄 2013年8月16日 - Pie chart showing water consumption. Image courtesy of EPA. Sample question: Make a pie chart in Excel that represents what percentage of people in a certain town own certain types of pets: dogs (1110 people), cats (987 people), rodents (312 people), reptiles (97 people), fish (398 people). Step 1: Type ..."}
        };
        String[][] result = new String[N][];
        int length = all.length;
        int i = 0;
        int j = 0;
        while (i < N) {
            if (j >= length)
                j = 0;
            result[i] = all[j];
            i++;
            j++;
        }
        return result;
    }

    /**
     * 根据关键字查询name和info，同时范围查询age
     * @param keywords
     * @param ageLow
     * @param ageHigh
     * @return
     */
    public List<Person> searchPersonsByKeyword(String keywords, Integer ageLow, Integer ageHigh) {

        logger.info("search start" + keywords + "::" + ageLow + "::" + ageHigh);
        if (keywords == null) {
            keywords = "";
        }
        List<Person> personList = new ArrayList<>();
        IndexSearcher indexSearcher = getIndexSearcher();
        Analyzer analyzer = new IKAnalyzer();
        BooleanQuery.Builder booleanQueryBuilder = new BooleanQuery.Builder();
        Query parserQuery = null;

        String[] fields = {"name", "info"};
        //创建查询解析对象
        MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, analyzer); //fileName是默认域
        String keyWords = QueryParser.escape(keywords);
        //fuzzyQuery 模糊匹配查询
//        keyWords = keyWords.join("~ ",
//                keyWords.split(" ")) + "~";
        // now queryString will be "some~ string~ to~ search~ for~"
        String oldKeyWords = keyWords;
        Query oldParserQuery = null;
        keyWords = replaceEnglishWord(keyWords);
        try {
            //设置允许前置通配符
            parser.setAllowLeadingWildcard(true);
            oldParserQuery = parser.parse(oldKeyWords);
            parserQuery = parser.parse(keyWords);
            logger.info("kew words: " + keyWords + ", parserQuery is: " + parserQuery);
            booleanQueryBuilder.add(new BooleanClause(parserQuery, BooleanClause.Occur.MUST));
            //test fuzzyQuery 模糊匹配
//            parserQuery = new FuzzyQuery(new Term("info", keyWords),2);
//            booleanQueryBuilder.add(new BooleanClause(parserQuery, BooleanClause.Occur.MUST));
            Query query;
            query = LongPoint.newRangeQuery("age", ageLow == null ? 0l : ageLow, ageHigh == null ? Long.MAX_VALUE : ageHigh);

            logger.info("kew words: " + ageLow + ageHigh + ", parserQuery is: " + query);
            booleanQueryBuilder.add(new BooleanClause(query, BooleanClause.Occur.MUST));
            BooleanQuery booleanQuery = booleanQueryBuilder.build();
            //查询数据
            TopDocs results;
            int size = 100;
            results = indexSearcher.search(booleanQuery, size);
            ScoreDoc[] scoreDocs = results.scoreDocs;

            Highlighter highlighter = null;
            if (parserQuery != null) {
                //设置Highlighter类
                Formatter formatter = new SimpleHTMLFormatter("<span style='color:red;'>", "</span>");
                QueryScorer fragmentScorer = new QueryScorer(parserQuery);
//                fragmentScorer.setExpandMultiTermQuery(true);
//                System.out.println(fragmentScorer);
                highlighter = new Highlighter(formatter, fragmentScorer);
                Fragmenter fragmenter = new SimpleSpanFragmenter(fragmentScorer);
                highlighter.setTextFragmenter(fragmenter);
            }
//            UnifiedHighlighter unifiedHighlighter = new UnifiedHighlighter(indexSearcher, analyzer);
//            String[] names = unifiedHighlighter.highlight("info", parserQuery, results);

            for (int i = 0; i < scoreDocs.length; i++) {
                //获取文档id
                int docId = scoreDocs[i].doc;
                //获得文档对象
                Document resultDoc;
                resultDoc = indexSearcher.doc(docId);
                Person person = new Person();
//                TokenStream tokenStream = analyzer.tokenStream("info", resultDoc.get("info"));
//                CharTermAttribute cta = tokenStream.addAttribute(CharTermAttribute.class);
//                while (tokenStream.incrementToken()) {
//                    System.out.print("[" + cta + "]");
//                }
//                String info1 = highlighter.getBestFragment(tokenStream, resultDoc.get("info"));
//                System.out.println(info1);
                if (highlighter != null) {
                    String name = highlighter.getBestFragment(new IKAnalyzer(), "name", resultDoc.get("name"));
                    person.setName(name == null ? resultDoc.get("name") : name);
                    String info = highlighter.getBestFragment(new IKAnalyzer(), "info", resultDoc.get("info"));
                    person.setInfo(info == null ? resultDoc.get("info") : info);
                }
//                person.setName(resultDoc.get("name"));
//                person.setInfo(resultDoc.get("info"));
                person.setAge(Integer.parseInt(resultDoc.get("age")));
                personList.add(person);
            }
        } catch (IOException e) {
            logger.error("io异常", e);
        } catch (Exception e) {
            logger.error("解析异常", e);
        }
        return personList;
    }

    /**
     * 根据关键字查询name和info
     * @param keywords
     * @return
     */
    public List<Person> searchPersonsByKeyword(String keywords){
        return searchPersonsByKeyword(keywords,null,null);
    }

    public static void main(String[] args) throws IOException, InvalidTokenOffsetsException {
//        WildcardQueryDemo wildcardQueryDemo = new WildcardQueryDemo();
//        wildcardQueryDemo.personListToIndex();
//        System.out.println(replaceEnglishWord("中国 高速 busii 你好 agcs  agd fsda 令计划"));
        testWildcardHighlight();
    }

    /**
     * 将英文转前后正则匹配
     * @param origin
     * @return
     */
    public static String replaceEnglishWord(String origin) {
        StringBuilder result = new StringBuilder();
        String[] strings = origin.split("\\s+");
        for (String string : strings) {
            if (string.matches("^[a-zA-Z]*")) {
                result.append("*");
                result.append(string);
                result.append("*");
            } else {
                result.append(string);
            }
            result.append(" ");
        }
        return result.toString();

    }

    /**
     * 测试通配符查询高亮
     * @throws IOException
     * @throws InvalidTokenOffsetsException
     */
    public static void testWildcardHighlight() throws IOException, InvalidTokenOffsetsException {
        String imei = "1234567890";
        KeywordAnalyzer analyzer = new KeywordAnalyzer();
        Query query = new WildcardQuery(new Term("IMEI", "*2345*"));
        QueryScorer scorer = new QueryScorer(query);
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
        Formatter formatter = new SimpleHTMLFormatter("<b>", "</b>");
        Highlighter highlighter = new Highlighter(formatter, scorer);
        System.out.println(highlighter.getBestFragment(analyzer, "IMEI", imei));
    }


}
