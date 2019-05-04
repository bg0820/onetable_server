
import java.util.List;

import org.snu.ids.kkma.ma.CharSetType;
import org.snu.ids.kkma.ma.MExpression;
import org.snu.ids.kkma.ma.Morpheme;
import org.snu.ids.kkma.ma.MorphemeAnalyzer;
import org.snu.ids.kkma.ma.Sentence;

public class HangleAnalyze {

	private static class HangleAnalyzeLazy {
		public static final HangleAnalyze LAZY_INSTANCE = new HangleAnalyze();
	}

	public static HangleAnalyze getInstance() {
		return HangleAnalyzeLazy.LAZY_INSTANCE;
	}

	private MorphemeAnalyzer ma;

	public void initilze() {
		ma = new MorphemeAnalyzer();
		ma.createLogger(null);
	}

	public void analyze(String content) {
		try {
			List<MExpression> ret = ma.analyze(content);

			ret = ma.postProcess(ret);
			ret = ma.leaveJustBest(ret);

			List<Sentence> stl = ma.divideToSentences(ret);
			for (int i = 0; i < stl.size(); i++) {
				Sentence st = stl.get(i);

				System.out.println("=============================================  " + st.getSentence());

				for (int j = 0; j < st.size(); j++) {
					for (int k = 0; k < st.get(j).size(); k++) {
						Morpheme mo = st.get(j).get(k);
						String charStr = mo.getString();
						String tagStr = mo.getTag();
						// System.out.println(charStr+", "+tagStr);

						if(mo.getCharSet() == CharSetType.NUMBER)
						{
							if(k + 1 < st.get(j).size() )
							{
								Morpheme nextMo = st.get(j).get(k + 1);
								System.out.println(mo.getString());
								System.out.println(nextMo.getString());

							}

						}
						/*
						System.out.println(mo.toString());
						if (tagStr.equals("NR") || tagStr.equals("OL")) {
							System.out.println(charStr);
						}
						if (tagStr.equals("NNG") || tagStr.equals("UN")) {
							if (j - 1 > 0) {
								if (st.get(j - 1).get(k).getTag().equals("NR")) {
									System.out.println(charStr);
								}
							}
						}*/
					}
					/*
					 * for(int k = 0; k< ss.length ; k++) { String str[] = ss[k].split("\\/");
					 * String charStr = str[0]; String tagStr = str[1]; if(tagStr.equals("NR")) {
					 *
					 * //String strr[] = ss[k+1].split("\\/"); //if(strr[1].equals(")) }
					 *
					 * }
					 */

				}

				System.out.println("=============================================");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
