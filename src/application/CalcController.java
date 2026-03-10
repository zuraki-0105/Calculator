package application;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class CalcController {
	@FXML TextField textField;
	private static boolean point_checker = true;
	
	//入力式を標準出力.
	public void printFormula(List<String> list, String name) {		
		System.out.println(name + ":");
		for (String l : list) {
			System.out.printf("* %s *, ", l);
		}
		System.out.println();
	}

	//配列から空白を取り除いてリスト化.
	public List<String> mkRmEmptyList(String[] array) {		
		List<String> arraylist = new ArrayList<>();
		
		for(String str : array) {
			if(!str.isEmpty()) {
				arraylist.add(str);
			}
		}
		
		return arraylist;
	}
	
	@FXML private void CB_num(ActionEvent event) {
		Button cb = (Button) event.getSource();
		String buttonText = cb.getText();
		String text = textField.getText();
		
		if(text.isEmpty()) {
			textField.setText(buttonText);
		} 
		else {
			char firstChar  = text.charAt(0);
			
			//"."を含むなら、通常入力.
			if(text.contains(".")) {
				textField.setText(text + buttonText);
				textField.positionCaret(textField.getText().length());
			} 
			//"."を含まずに一文字目が０なら、上書き.
			else if(firstChar == '0') {
				textField.setText(buttonText);
			} 
			//上記以外では、通常入力.
			else {
				textField.setText(text + buttonText);
				textField.positionCaret(textField.getText().length());
			}
		}		
	}
	
	@FXML private void CB_0() {
		String text = textField.getText();
		
		if(text.isEmpty()) {
			textField.setText("0");
		}
		else {
			char firstChar = text.charAt(0);
			
			//"."を含まず、一文字目が0のとき入力しない.
			if(text.contains(".") || firstChar != '0') {
				textField.setText(text + "0");
				textField.positionCaret(textField.getText().length());
			}
		}
	}
	
	
	@FXML private void CB_sign(ActionEvent event) {
		Button cb = (Button) event.getSource();
		String buttonText = cb.getText();
		String text = textField.getText();
		
		//テキストフィールドが空欄なら入力しない.
		if(!text.isEmpty()) {
			char lastChar = text.charAt(text.length() - 1);
			
			//最後の文字が"."を除く演算記号ならば.
			if((lastChar == '+' || lastChar == '-' || lastChar == '×' || lastChar == '÷') && !buttonText.equals(".")) {
				textField.setText(text.substring(0, text.length() - 1) + buttonText);
				textField.positionCaret(textField.getText().length());
				point_checker = true;
			}
			//最後の文字が数字なら.
			else if(lastChar != '+' && lastChar != '-' && lastChar != '×' && lastChar != '÷' && lastChar != '.') {
				//ボタンが"."を除く演算記号ならばTrueに.
				if(!buttonText.equals(".")) {
					textField.setText(text + buttonText);
					textField.positionCaret(textField.getText().length());
					point_checker = true;
				} 
				//ボタンが"."ならばpoint_checkerを参照して処理を行う.
				else {
					if(point_checker) {
						textField.setText(text + buttonText);
						textField.positionCaret(textField.getText().length());
						point_checker = false;
					} 
				}
			}
		}
//		System.out.println(point_checker);
	}
	
	@FXML private void CB_equal() {
		String text = textField.getText();
		
		if(!text.isEmpty()) {
			char lastChar = text.charAt(text.length() - 1);
			
			if(lastChar == '+' || lastChar == '-' || lastChar == '×' || lastChar == '÷' || lastChar == '.') {
				text = text.substring(0, text.length() - 1);
				textField.setText(text);
			}
			
			//数字と記号で分割.
			String[] numbers = text.split("[^0-9\\.]+");
			String[] operators = text.split("[0-9\\.]+");
			
			//空白削除.
			List<String> numberList = mkRmEmptyList(numbers); 
			List<String> operatorList = mkRmEmptyList(operators); 
//			printFormula(numberList, "数字");
//			printFormula(operatorList, "演算子");
			
			//式の項ごとに分割、統合.
			List<String> forCalcList = new ArrayList<>();
			
			forCalcList.add(numberList.get(0));
			for(int numCount = 1, opCount = 0; numCount < numberList.size(); numCount++, opCount++) {
				forCalcList.add(operatorList.get(opCount));
				forCalcList.add(numberList.get(numCount));
			}
			List<String> copyForCalcList = new ArrayList<>(forCalcList);
//			printFormula(forCalcList, "式");
			
			
			//÷,×,-,+の順番で計算し、終わったらcontinue.
			outer : for(int i = 0; i < operatorList.size(); i++) {
				String[] calcOrder = {"÷", "×", "-", "+"};
				for(String op : calcOrder) {
					if(calculate(forCalcList, op)) continue outer;
				}
			}
			double ans = Double.parseDouble(forCalcList.get(0));
			
			setAnswer(ans, copyForCalcList);
			
		}
	}
	
	//引数の演算子が参照されたらtrueを返す.
	public boolean calculate(List<String> list, String op) {
		if(list.contains(op)) {
			int[] indexPrevNext = new int[2];
			int countIndex = 0;
			
			//引数の演算子と計算対象の数字のインデックスを取る.
			for(countIndex = 0; countIndex < list.size(); countIndex++) {
				if(list.get(countIndex).equals(op)) {
					indexPrevNext[0] = countIndex - 1;
					indexPrevNext[1] = countIndex + 1;
					break;
				}
			}
			
			double a = Double.parseDouble(list.get(indexPrevNext[0]));
			double b = Double.parseDouble(list.get(indexPrevNext[1]));
			
			double ans =  switch(op) {
				case "+" -> a + b;
				case "-" -> a - b;
				case "×" -> a * b;
				case "÷" -> a / b;
				default -> {
					System.out.println("Error ");
		            yield Double.NaN;
				}
			};
			list.set(indexPrevNext[0], String.valueOf(ans));
			list.remove(countIndex);
			list.remove(indexPrevNext[1] - 1); //=list.remove(countIndex);
			
			return true;
		}
		return false;
	}
	
	//テキストフィールドに計算結果を表示.
	public void setAnswer(double ans, List<String> list) {
		for(String str : list) System.out.print(str);
		//整数なら.
		if(ans % 1 == 0) {
			textField.setText(String.valueOf((int)ans));
			System.out.println(" = " + (int)ans);
		} else {
			textField.setText(String.valueOf(ans));
			System.out.println(" = " + ans);
		}
	}
	
	@FXML private void CB_delete() {
		if(!textField.getText().isEmpty()) {
			textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
		}
		
	}
	
	@FXML private void CB_clear() {
		textField.clear();
		point_checker = true;
	} 
}

