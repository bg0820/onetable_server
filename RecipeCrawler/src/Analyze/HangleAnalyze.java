package Analyze;

import java.util.ArrayDeque;
import java.util.List;
import org.snu.ids.kkma.ma.CharSetType;
import org.snu.ids.kkma.ma.Eojeol;
import org.snu.ids.kkma.ma.MExpression;
import org.snu.ids.kkma.ma.Morpheme;
import org.snu.ids.kkma.ma.MorphemeAnalyzer;
import org.snu.ids.kkma.ma.Sentence;
import Model.IngredientUnit;

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

	public IngredientUnit analyze(String content) throws Exception {

		// System.out.println(content); //재료의 양

		if (content.equals(""))
			return null;

		List<MExpression> ret = ma.analyze(content);

		ret = ma.postProcess(ret);
		ret = ma.leaveJustBest(ret);
		
		List<Sentence> stl = ma.divideToSentences(ret);
		
		IngredientUnit unit = new IngredientUnit();

		String unitStr = "";
		String symbol = "";
		double min = 0;
		double max = 0;
		int num = 0;

		ArrayDeque<Morpheme> pickUpQueue = new ArrayDeque<Morpheme>();

		
		Sentence st = stl.get(0);

		for (int i = 0; i < st.size(); i++) {
			Eojeol eoj = st.get(i);
			// System.out.println(eoj.getSmplStr2());


			for (int j = 0; j < eoj.size(); j++) {

				Morpheme mp = eoj.get(j);
			
				if (mp.getTag().equals("ETD")) {
					// ETD가 나오는경우 앞의 값이 VCP 일경우 앞에 제거하고 합쳐진것 넣기
					Morpheme prevMor = eoj.get(j - 1);
					if (prevMor.getTag().equals("VCP")
							|| prevMor.getTag().equals("VA") || prevMor.getTag().equals("VV") ) {
						Morpheme mpp = new Morpheme(eoj.getExp(), null, "C");
						pickUpQueue.pollLast();
						pickUpQueue.add(mpp);
					} else
						pickUpQueue.add(mp);
				} else {
					pickUpQueue.add(mp);
				}


			}
		}

		while (true) {
			Morpheme charMorp = pickUpQueue.poll();
			if (charMorp == null)
				break;

			if (charMorp.getCharSet() == CharSetType.NUMBER) {
				if (num == 0)
					min = Double.parseDouble(charMorp.getString().replaceAll("\\,", ""));
				else if (num == 1)
					max = Double.parseDouble(charMorp.getString().replaceAll("\\,", ""));

				num++;

			} else if (charMorp.getCharSet() == CharSetType.SYMBOL)
				if(charMorp.getTag().equals("SS"))
				{
					unitStr+= charMorp.getString();
				} else
					symbol = charMorp.getString();
			else {
				unitStr += charMorp.getString();
			}

		}


		unit.setMin(min);
		unit.setMax(max);
		unit.setUnitStr(unitStr);
		unit.setSymbol(symbol);
		
		return unit;
		//System.out.println(unit.toString()); //재료당 양의 값 세분화

	}

}
