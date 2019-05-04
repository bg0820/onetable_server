

import java.util.List;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.snu.ids.kkma.ma.CharSetType;
import org.snu.ids.kkma.ma.Eojeol;
import org.snu.ids.kkma.ma.MExpression;
import org.snu.ids.kkma.ma.MorphemeAnalyzer;
import org.snu.ids.kkma.ma.Sentence;

public class HangleAnalyze {
	
	private static class HangleAnalyzeLazy {
		public static final HangleAnalyze LAZY_INSTANCE = new HangleAnalyze();
	}
	
	public static HangleAnalyze getInstance()
	{
		return HangleAnalyzeLazy.LAZY_INSTANCE;
	}
	
	private MorphemeAnalyzer ma;
	
	public void initilze()
	{
		ma = new MorphemeAnalyzer();
        ma.createLogger(null);
	}
	
	public void analyze(String content)
	{
		try {
            List<MExpression> ret = ma.analyze(content);
            
            ret = ma.postProcess(ret);
            ret = ma.leaveJustBest(ret);
            
            List<Sentence> stl = ma.divideToSentences(ret);
            for( int i = 0; i < stl.size(); i++ ) {
                  Sentence st = stl.get(i);
                  System.out.println("=============================================  " + st.getSentence());
                  for( int j = 0; j < st.size(); j++ ) {
                	  Eojeol eo = st.get(j);
                 	 
                      System.out.println(eo.getExp() + "\t" + eo.getSmplStr2() + "\t" + eo.getStartIndex() + "\t" + eo.getFirstMorp() + "\t" + eo.getLastMorp());
                
                  }
                  
                  System.out.println("=============================================");
            }
      } catch (Exception e) {
            e.printStackTrace();
      }
	}
}
