package glit.onetable.model.vo;

import java.util.ArrayList;
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
	private List<String> unitList = new ArrayList<String>();

	public void initilze() {
		ma = new MorphemeAnalyzer();
		ma.createLogger(null);

		unitList.add("kg");
		unitList.add("ml");
		unitList.add("l");
		unitList.add("cc");
		unitList.add("개");
		unitList.add("g");
		unitList.add("cm");
	}

	public AnalyzeVariety analyze(String content) {
		try {
			//String resultContent = content.replaceAll("\\p{Z}", "");
			List<MExpression> ret = ma.analyze(content);

			ret = ma.postProcess(ret);
			ret = ma.leaveJustBest(ret);
			AnalyzeVariety av = new AnalyzeVariety();
			double unitNum = 0;
			String unitStr = "";
			int unitCnt = 0;

			List<Sentence> stl = ma.divideToSentences(ret);
			for (int i = 0; i < stl.size(); i++) {
				Sentence st = stl.get(i);

				System.out.println(st);
				for (int j = 0; j < st.size(); j++) {
					// j = 띄어쓰기
					// k = 형태소
					for (int k = 0; k < st.get(j).size(); k++) {
						Morpheme mo = st.get(j).get(k);


						 System.out.println("-----------------------");
						 System.out.println(mo.getComposed());
						  System.out.println(mo.getCharSetName());
						 System.out.println(mo.getSmplStr());
						 System.out.println(mo.getSmplStr2()); System.out.println(mo.getString());
						 System.out.println(mo.getTag()); System.out.println(mo.getTagNum());
						 System.out.println(mo.getCharSet());

						 System.out.println("-----------------------");


						if (mo.getCharSet() == CharSetType.NUMBER) {

							// 숫자 존재여부
							unitNum = Double.parseDouble(mo.getString().replaceAll("\\,", ""));

							if (k + 1 < st.get(j).size()) {
								Morpheme nextMo = st.get(j).get(k + 1);
								if (nextMo.getTag().equals("NNG") || nextMo.getTag().equals("OL"))
									unitStr = nextMo.getString();



							} else if (j + 1 < st.size()) {

								Morpheme nextMo = st.get(j + 1).get(0);

								if (nextMo.getTag().equals("NNG") || nextMo.getTag().equals("OL"))
									unitStr = nextMo.getString();


							}

							unitCnt++;

						}

					}

				}

				//
				if (unitNum != 0 && unitStr != "" && unitCnt == 1) {
					av.setDisplayName(content);
					av.setUnitNum(unitNum);
					av.setUnitStr(unitStr);

					return av;
				} else
					return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
