package Model;

import java.util.ArrayList;
import java.util.List;

import org.snu.ids.kkma.ma.CharSetType;
import org.snu.ids.kkma.ma.Eojeol;
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
	private ArrayList<String> unitList = new ArrayList<String>();

	public void initilze() {
		ma = new MorphemeAnalyzer();
		ma.createLogger(null);
		unitList.add("kg");
		unitList.add("g");
		unitList.add("cm");
		unitList.add("개");
		unitList.add("l");
		unitList.add("ml");
		unitList.add("cc");
		unitList.add("m");
	}

	public AnalyzeUnit analyze(String content) {
		try {
			String resultContent = content.replaceAll("\\p{Z}", "");
			List<MExpression> ret = ma.analyze(resultContent);

			ret = ma.postProcess(ret);
			ret = ma.leaveJustBest(ret);
			AnalyzeUnit anaUnit = new AnalyzeUnit();
			
			double unitNum = 0;
			String unitStr = "";
			boolean isSelect =  false;

			List<Sentence> stl = ma.divideToSentences(ret);
			Sentence st = stl.get(0);

			for (int i = 0; i < st.size(); i++) {
				Eojeol eoj = st.get(i);
				// System.out.println(eoj.getSmplStr2());

				//  = 띄어쓰기
				// j = 형태소
				for (int k = 0; k < eoj.size(); k++) {
					Morpheme mo = eoj.get(k);
					
						 
					/*
						 System.out.println(mo.getCharSetName());
						 System.out.println(mo.getComposed());
						 System.out.println(mo.getSmplStr());
						 System.out.println(mo.getSmplStr2());
						 System.out.println(mo.getString());
						 System.out.println(mo.getTag()); 
						 System.out.println(mo.getTagNum());
						 System.out.println(mo.getCharSet());
						
						*/
					
						if (mo.getCharSet() == CharSetType.NUMBER) {
							if (k + 1 < eoj.size()) {
								
								Morpheme nextMo = eoj.get(k + 1);
								//System.out.println(nextMo.getString());
								
								for(int unitIdx = 0; unitIdx < unitList.size(); unitIdx++)
								{
									if(nextMo.getString().contains(unitList.get(unitIdx)))
									{
										// 곱하기 기호가 없는경우에만
										//if(!nextMo.getString().toLowerCase().contentEquals("x") || !nextMo.getString().toLowerCase().contentEquals("*"))
										//{
											unitNum = Double.parseDouble(mo.getString().replaceAll("\\,", ""));
											isSelect = true;
											unitStr = unitList.get(unitIdx); 
											break;
										//}				
										
									}
								}
								
							} else if (i + 1 < st.size()) {
								// 띄어쓰기
							}
							

					}
				}
				
			}
			if (isSelect) {
				anaUnit.setUnitAmount(unitNum);
				anaUnit.setUnitStr(unitStr);
				
				return anaUnit;
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
