var nativeCreate=require("./_nativeCreate"),HASH_UNDEFINED="__lodash_hash_undefined__",objectProto=Object.prototype,hasOwnProperty=objectProto.hasOwnProperty;function hashGet(e){var t=this.__data__;if(nativeCreate){var r=t[e];return r===HASH_UNDEFINED?void 0:r}return hasOwnProperty.call(t,e)?t[e]:void 0}module.exports=hashGet;