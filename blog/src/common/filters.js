/**
 * @description: 返回形如 年月日 时:分:秒格式字符串
 * @param {Date} value
 * @return {String}
 */
function dateTime(value) {
  return getDate(value, 1);
}

/**
 * @description: 返回形如 年-月-日 时:分:秒格式字符串
 * @param {Date} value
 * @return {String}
 */
function dateTimeLong(value) {
  return getDate(value, 0);
}

/**
 * @description: 返回形如 年月日格式字符串
 * @param {Date} value
 * @return {String}
 */
function date(value) {
  return getDate(value, 2);
}

/**
 * @description: 日期时间格式化,可以返回多种格式字符串
 * @param {Date} value
 * @param {Number} choose
 * @return {String}
 */
function getDate(value, choose) {
  var date = new Date(value);

  var years = date.getFullYear();
  var months = (date.getMonth() + 1).toString().padStart(2, "0");
  var days = date
    .getDate()
    .toString()
    .padStart(2, "0");
  var hours = date
    .getHours()
    .toString()
    .padStart(2, "0");
  var minutes = date
    .getMinutes()
    .toString()
    .padStart(2, "0");
  var seconds = date
    .getSeconds()
    .toString()
    .padStart(2, "0");
  if (choose === 1) {
    return `${years}年${months}月${days}日 ${hours}:${minutes}:${seconds}`;
  } else if (choose === 0) {
    return `${years}-${months}-${days} ${hours}:${minutes}:${seconds}`;
  } else if (choose === 2) {
    return `${years}年${months}月${days}日`;
  }
}

/**
 * @description: 返回指定月份中文字符串
 * @param {Number} value
 * @return {String}
 */
function months(value) {
  var months = [
    "",
    "一月",
    "二月",
    "三月",
    "四月",
    "五月",
    "六月",
    "七月",
    "八月",
    "九月",
    "十月",
    "十一月",
    "十二月"
  ];
  var month = months[value];

  return `${month}`;
}

/**
 * @description: 返回形如 月份 日，年格式字符串
 * @param {Date} value
 * @return {String}
 */
function monthDayYear(value) {
  // 根据给定的字符串，得到特定的日期
  var date = new Date(value);
  var year = date.getFullYear();

  var months = [
    "一月",
    "二月",
    "三月",
    "四月",
    "五月",
    "六月",
    "七月",
    "八月",
    "九月",
    "十月",
    "十一月",
    "十二月"
  ];
  var month = months[date.getMonth()];
  //该方法是在字符串前使用 fillString填充，直到字符串长度为 maxLength,这样字符串就自动在单位数前面补0.
  var day = date
    .getDate()
    .toString()
    .padStart(2, "0");

  return `${month} ${day}, ${year}`;
}

/**
 * @description: 数字过滤器：不足十位自动补0
 * @param {Number} value
 * @return {String}
 */
function count(value) {
  var num = value.toString().padStart(2, "0");
  return `${num}`;
}

/**
 * @description: 文章标题过滤器：限制标题为15字符超过加...
 * @param {String} value
 * @return {String}
 */
function title(value) {
  if (value.length > 15) {
    value = value.substring(0, 15) + "...";
  }
  return `${value}`;
}

export function dateTimeFormat(time) {
  return dateTime(time);
}
export function dateFormat(time) {
  return date(time);
}
export function dateTimeLongFormat(time) {
  return dateTimeLong(time);
}
export function monthsFormat(month) {
  return months(month);
}
export function countFormat(value) {
  return count(value);
}
export function titleFormat(value) {
  return title(value);
}
export function monthDayYearFormat(value) {
  return monthDayYear(value);
}
