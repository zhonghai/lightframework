function formatJson(str){  
            var   reg   =   /\s/g;   
            var   str   =   str.replace(reg,   "&nbsp");   
            var level = 0;  
            var level_d = 0 ;  
            var result = "";  
            var pre_ch = false;  
            var last_ch = false;  
            var space_cur_ch = false;  
            //   
            var cur_comma = false;  
            for(var i = 0 ;i < str.length; i++){  
                var ch = str.charAt(i);  
                pre_ch = false;  
                cur_comma = false;  
                space_cur_ch = false;  
                if(ch === ' '){  
                    pre_ch = last_ch;  
                    space_cur_ch = true;  
                }else if( ch ===','){  
                    ch = ',\r\n';  
                    pre_ch = true;  
                    cur_comma = true;  
                }else if(ch ==='['){  
                    ch =  prefix(level*4,cur_comma) + '[\r\n' ;  
                    level ++;  
                    pre_ch = true;  
                }else if(ch ==='{'){  
                    ch = prefix(level*4,cur_comma) +'{\r\n';  
                    level_d ++;  
                    pre_ch = true;  
                }else if(ch ===']'){  
                    level --;  
                    ch = '\r\n' + prefix(level*4,cur_comma) +']';  
                    pre_ch = true;  
                }else if(ch ==='}'){  
                    level_d --;  
                    ch = '\r\n'+ prefix(level*4,cur_comma) +'}';  
                    pre_ch = true;  
                }else{  
                    if(last_ch ){  
                        ch =  prefix(level*4 + level_d*2,cur_comma) + ch;  
                    }  
                      
                }     
                if(!space_cur_ch){  
                    result += ch;  
                }  
                  
                last_ch = pre_ch;  
            } 
            result = result.replace(/&nbsp/g," "); 
            return result;  
        }  
  
          
function prefix(times,comma){  
    var prefix = "";  
    if(!comma){  
        for(var i = 0 ; i<times;i++){  
            prefix = prefix + ' ';  
        }  
    }  
    return prefix;  
}

