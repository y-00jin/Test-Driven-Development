package chap02;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *     # 암호 검사기
 *     # 조건
 *     ## 1. 길이 8글자 이상
 *     ## 2. 0부터 9사이의 숫자를 포함
 *     ## 3. 대문자 포함
 */
public class PasswordStrengthMeterTest {    // 암호 검사기

    private PasswordStrengthMeter meter = new PasswordStrengthMeter();

    private void assertStrength(String password, PasswordStrength expStr){
        PasswordStrength result = meter.meter(password);
        assertEquals(expStr , result);
    }

    @Test
    void nullInput_Then_Invalid(){      // 값이 없는 경우
        assertStrength(null, PasswordStrength.INVALID);
    }

    @Test
    void emptyInput_Then_Invalid(){     // 빈값인 경우
        assertStrength("", PasswordStrength.INVALID);
    }

    @Test
    void meetsAllCriteria_Then_Strong(){    // 모든 조건 충족 : 강함
        assertStrength("ab12!@AB", PasswordStrength.STRONG);
        assertStrength("abc1!Add", PasswordStrength.STRONG);
    }

    @Test
    void meetsOtherCriteria_except_for_Length_Then_Normal(){    // 길이 충족X, 나머지 2개 조건 충족 : 보통
        assertStrength("ab12!@A", PasswordStrength.NORMAL);
        assertStrength("Ab12!c", PasswordStrength.NORMAL);
    }

    @Test
    void meetsOtherCriteria_except_for_number_Then_Normal(){    // 숫자 충족X, 나머지 2개 조건 충족 : 보통
        assertStrength("ab!@ABqwer", PasswordStrength.NORMAL);
    }

    @Test
    void meetsOtherCriteria_except_for_Uppercase_Then_Normal(){ // 대문자 충족X, 나머지 2개 조건 충족 : 보통
        assertStrength("ab12!@df", PasswordStrength.NORMAL);
    }

    @Test
    void meetsOnlyLengthCriteria_Then_Weak(){       // 길이만 충족 : 약함
        assertStrength("qwerasdf", PasswordStrength.WEAK);
    }

    @Test
    void meetsOnlyNumCriteria_Then_Weak(){       // 숫자만 충족 : 약함
        assertStrength("12345", PasswordStrength.WEAK);
    }

    @Test
    void meetsOnlyUpperCriteria_Then_Weak(){       // 대문자만 충족 : 약함
        assertStrength("ASDF", PasswordStrength.WEAK);
    }

    @Test
    void meetsNoCriteria_Then_Weak(){
        assertStrength("abc", PasswordStrength.WEAK);
    }
}
