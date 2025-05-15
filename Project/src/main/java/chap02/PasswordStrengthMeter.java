package chap02;

public class PasswordStrengthMeter {
    public PasswordStrength meter(String password){

        if(password == null || password.isEmpty() ) return PasswordStrength.INVALID;        // 값 없음
        int count = getMetCriteriaCount(password);
        if(count <= 1) return PasswordStrength.WEAK;
        if(count == 2) return PasswordStrength.NORMAL;
        return PasswordStrength.STRONG;
    }

    // 숫자 검증
    private boolean meetsContainingNumberCriteria(String s){
        for(char ch : s.toCharArray()){
            if(ch >= '0' && ch <= '9'){
                return true;
            }
        }
        return false;
    }

    // 대문자 검증
    private boolean meetsContainingUppercaseCriteria(String s){
        for(char ch : s.toCharArray()){
            if(Character.isUpperCase(ch)) return true;
        }
        return false;
    }

    private int getMetCriteriaCount(String password){
        int count =0 ;
        if(password.length()  >= 8) count++;
        if(meetsContainingNumberCriteria(password)) count++;
        if(meetsContainingUppercaseCriteria(password)) count++;
        return count;
    }
}
