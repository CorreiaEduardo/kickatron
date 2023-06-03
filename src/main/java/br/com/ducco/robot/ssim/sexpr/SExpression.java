package br.com.ducco.robot.ssim.sexpr;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
@Getter
public class SExpression {
    private String value;

    public boolean contains(String... keys) {
        return Arrays.stream(keys).anyMatch(value::contains);
    }

    //TODO
    private Optional<SExpression> extract(String key) {
        Pattern pattern = Pattern.compile("\\(" + key + "\\s.*?\\)");
        Matcher matcher = pattern.matcher(this.value);

        if (matcher.find()) {
            return Optional.of(new SExpression(matcher.group()));
        }

        return Optional.empty();
    }
}
