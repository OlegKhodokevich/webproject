function sayHi() {
    console.log("Hello");
}
sayHi();
function replaceTitle(title) {
    const element = document.getElementById("title");
    element.innerText = title;
}
replaceTitle("By by");