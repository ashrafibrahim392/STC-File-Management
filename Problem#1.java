import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        System.out.println(reverseStringBetweenParentheses("((ng)ipm(ca))"));
    }


    static String reverseSubString(char stringAsArray[], int startIndex, int endIndex) {
        if (startIndex < endIndex) {

            char currentChar = stringAsArray[startIndex];
            if(currentChar != '('  && currentChar !=')'){
                stringAsArray[startIndex] = stringAsArray[endIndex];
                stringAsArray[endIndex] = currentChar;
            }
            reverseSubString(stringAsArray, startIndex + 1, endIndex - 1);
        }
        return String.copyValueOf(stringAsArray);
    }
    static String reverseStringBetweenParentheses(String input) {
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(') {
                stack.push(i);
            } else if (input.charAt(i) == ')') {
                input = reverseSubString(input.toCharArray(), stack.peek() + 1, i-1);
                stack.pop();
            }
        }


        return input;
    }


}