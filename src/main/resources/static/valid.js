// 유효성 검사
const autoHyphen = (target) => {
    target.value = target.value
        .replace(/[^0-9]/g, '')
        .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
}

const passwordRegex = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
const passwordRegexRule = "특수문자 / 문자 / 숫자 포함 형태의 8~15자리";

const koreanRegex = /^[가-힣]*$/;
const koreanRegexRule = "한글"