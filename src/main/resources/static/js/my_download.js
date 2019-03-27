/**
 * Created by 直到世界尽头 on 2018/4/17.
 */
function my_download(fileName,url){
    var form= document.createElement("form");   //定义一个form表单
    document.body.appendChild(form);
    form.setAttribute("style","display:none")
    form.setAttribute("target","")
    form.setAttribute("method","post")
    form.setAttribute("action",url)
    form.method = 'post';
    var fileInput= document.createElement("input");
    fileInput.setAttribute("type","hidden")
    fileInput.setAttribute("id","fileName")
    fileInput.setAttribute("name","modelName")
    fileInput.setAttribute("value",fileName)
    form.appendChild(fileInput);
    form.submit();//表单提交
}