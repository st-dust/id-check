package cz.zatisigroup.utills;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class ConvertToNumeric {
    public static String translate(String text) {
        String czechAlphabet = "+ěščřžýáíé";
        String alphabeticAlternative = "1234567890";

        //text = Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

        return StringUtils.replaceChars(text, czechAlphabet, alphabeticAlternative);
    }

    public static Optional<Integer> getNumber(String id) {
        return Optional.ofNullable(checkIfIsNumeric(translate(id)));
    }

    public static Integer checkIfIsNumeric(String id) {
        // TODO remove unused variable
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }
}
