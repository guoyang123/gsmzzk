import axios from 'axios';

let base = 'http://www.gsmzzk.com:8080';


var service=axios.create({

    baseURL:"http:/www.gsmzzk.com:8080",
    // 请求预处理函数 可以把你传的param进行处理
    withCredentials: true, // 允许携带cookie
    transformRequest: [
        data => {
            // data 就是你post请求传的值
            // 一下主要是吧数据拼接成 类似get格式
            let params = ''
            for (var index in data) {
                params += index + '=' + data[index] + '&'
            }
            return params
        }
    ]
})

export const requestLogin = params => { return service.post(`${base}/user/login`, params).then(res => res.data); };
export const getBaseInfos = params => { return service.get(`${base}/base/info`, { params: params }); };
export const getQualityInfos = params => { return service.get(`${base}/quality/info`, { params: params }); };


export const batchdownlaodBaseInfo = params => { return axios.get(`${base}/base/export`, { params: params }); };

export const batchdownlaodBaseInfo2 = params => {

   // return axios.get(`${base}/base/export`, { params: params });
    var xmlhttp;
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }


    xmlhttp.open("POST",`${base}/base/export?ids=`+params,true);

    xmlhttp.send()

   // xmlhttp.responseType="blob"
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            console.log(xmlhttp.responseText);

            	let blob = new Blob([xmlhttp.responseText], { type: 'application/vnd.ms-excel;charset=utf-8' });
            	let downloadElement = document.createElement('a');
            	let href = window.URL.createObjectURL(blob); //创建下载的链接
            	downloadElement.href = href;
            	downloadElement.download = 'baseinfo.xls'; //下载后文件名
            	document.body.appendChild(downloadElement);
            	downloadElement.click(); //点击下载
            	document.body.removeChild(downloadElement); //下载完成移除元素
            	window.URL.revokeObjectURL(href); //释放掉blob对象

        }
    }
};

export const batchdownlaodBaseInfo3 = params => {

    axios({
        method: "get",
        url: `${base}/base/export?ids=`+params,
        responseType: 'blob'
    }).then((res) => {
        const link = document.createElement('a')
        let blob = new Blob([res.data], {type: 'application/vnd.ms-excel'})
        link.style.display = 'none'
        link.href = URL.createObjectURL(blob)

        // link.download = res.headers['content-disposition'] //下载后文件名
        link.download ="基础信息.xls" //下载的文件名
        document.body.appendChild(link)
        link.click()
        document.body.removeChild(link)
    }).catch(error => {

        console.log(error)
    })



};
export const batchdownlaodQualityInfo = params => {

    axios({
        method: "get",
        url: `${base}/quality/export?ids=`+params,
        responseType: 'blob'
    }).then((res) => {
        const link = document.createElement('a')
        let blob = new Blob([res.data], {type: 'application/vnd.ms-excel'})
        link.style.display = 'none'
        link.href = URL.createObjectURL(blob)

        // link.download = res.headers['content-disposition'] //下载后文件名
        link.download ="质量指标.xls" //下载的文件名
        document.body.appendChild(link)
        link.click()
        document.body.removeChild(link)
    }).catch(error => {

        console.log(error)
    })



};

export const getUserList = params => { return axios.get(`${base}/user/list`, { params: params }); };

export const getUserListPage = params => { return axios.get(`${base}/user/listpage`, { params: params }); };

export const removeUser = params => { return axios.get(`${base}/user/remove`, { params: params }); };

export const batchRemoveUser = params => { return axios.get(`${base}/user/batchremove`, { params: params }); };

export const editUser = params => { return axios.get(`${base}/user/edit`, { params: params }); };

export const addUser = params => { return axios.get(`${base}/user/add`, { params: params }); };