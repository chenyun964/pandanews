var arrayProto=Array.prototype,nativeJoin=arrayProto.join;function join(o,r){return null==o?"":nativeJoin.call(o,r)}module.exports=join;