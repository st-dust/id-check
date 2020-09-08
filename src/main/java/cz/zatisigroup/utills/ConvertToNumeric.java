package cz.zatisigroup.utills;

import org.apache.commons.lang3.StringUtils;

public class ConvertToNumeric {
    public static String translate(String text) {
        String czechAlphabet = "+ěščřžýáíé";
        String alphabeticAlternative = "1234567890";

        //text = Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

        return StringUtils.replaceChars(text, czechAlphabet, alphabeticAlternative);
    }
}
