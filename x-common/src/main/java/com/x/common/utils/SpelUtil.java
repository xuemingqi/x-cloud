package com.x.common.utils;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class SpelUtil {

    public static String[] getParameters(String[] parameters, Method method, Object[] arguments) {
        EvaluationContext evaluationContext = getEvaluationContext(method, arguments);
        ExpressionParser parser = new SpelExpressionParser();
        String[] arr = new String[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            arr[i] = parser.parseExpression(parameters[i]).getValue(evaluationContext, String.class);
        }
        return arr;
    }

    public static EvaluationContext getEvaluationContext(Method method, Object[] arguments) {
        Parameter[] parameters = method.getParameters();
        EvaluationContext evaluationContext = new StandardEvaluationContext(method);

        for (int i = 0; i < parameters.length; i++) {
            String parameterName = parameters[i].getName();
            evaluationContext.setVariable(parameterName, arguments[i]);
        }

        return evaluationContext;
    }
}
