var eq=require("./eq"),objectProto=Object.prototype,hasOwnProperty=objectProto.hasOwnProperty;function customDefaultsAssignIn(o,t,e,r){return void 0===o||eq(o,objectProto[e])&&!hasOwnProperty.call(r,e)?t:o}module.exports=customDefaultsAssignIn;