
import CryptoJS from 'crypto-js'

const key = CryptoJS.enc.Utf8.parse('MedicineSystem')
const iv = CryptoJS.enc.Utf8.parse('1234567887654321')

// 加密
function encrypt (text) {
  let encryCode =  CryptoJS.AES.encrypt(text, CryptoJS.enc.Utf8.parse(key), {
    iv: CryptoJS.enc.Utf8.parse(iv),
    mode: CryptoJS.mode.CBC,
    padding: CryptoJS.pad.Pkcs7
  }).toString()
 return CryptoJS.enc.Base64.stringify(CryptoJS.enc.Utf8.parse(encryCode));
}

// 解密
function decrypt (text) {
  text = CryptoJS.enc.Base64.parse(text).toString(CryptoJS.enc.Utf8);
  return CryptoJS.AES.decrypt(text, CryptoJS.enc.Utf8.parse(key), {
    iv: CryptoJS.enc.Utf8.parse(iv),
    mode: CryptoJS.mode.CBC,
    padding: CryptoJS.pad.Pkcs7
  }).toString(CryptoJS.enc.Utf8)
}

export default {
  encrypt,
  decrypt
}
