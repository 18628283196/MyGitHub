---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by zhanglong.
--- DateTime: 2019/7/1 14:29
---
--[[
多行注释
]]--

--单行注释
print("hello lua")
function func()
    a = 10 ; --全局变量
    local b = 20;--局部变量
    return b
end
func()
--获取函数体内的全局变量，需要先调用函数，初始化变量
print(a)
--获得函数体内局部变量可以通过return返回，调用函数获取返回值
print(func())
print(type(10))
print(type(func()))
print(type("hello lua"))

print("==================赋值运算=====================")

str = "hello" .. "lua"   --连接符..
print(str)

a,b = 10 , 20;
print(a,b)

c,b,d = 10, 20.0;
print(a,b,d)

print("==================算数运算=====================")

a,b = 10,20
print("a+b="..a+b)
print("a-b="..a-b)
print("a*b="..a*b)
print("a/b="..a/b)
print("a%b="..a%b)
print("a^2="..a^2)

print("==================关系运算=====================")

print(a>b)
print(a<b)
print(a>=b)
print(a<=b)
print(a==b)
print(a~=b)

print("==================逻辑运算=====================")

print(true and false)  --相当于 &&
print(true and true)
print(true or false)     --相当于 ||
print(true or true)
print(not true)

print("==================其它运算=====================")

str = "helloword"
print(#str)

print("==================条件控制语句(if)=====================")

if (true) then
    print("ok")
end

a,b = 10,20
if (a>b) then
    print("a>b")
else
    print("a<b")
end

c=20
if(c>10)
then
    if(c<30)
    then
        print(c.."的值在10到30之间")
    end
end

print("==================循环控制语句(while)=====================")

a=10
while(a>0) do
    print(a)
    a = a-1
end

-- repeat until 重复指定循环  直到指定条件为真才停止循环

b=10
repeat
    print(b)
    b=b-1
until (b<1)

print("==================数组=====================")

arr = {"aaa","bbb","ccc"}
for i = 1, #arr do
    print(arr[i])
end

--使用泛型for遍历数组
for i ,v in ipairs(arr) do
    print("下标是:"..i,"元素是："..v)
end

for i,v in pairs(arr) do
    print("下标是:"..i,"元素是："..v)
end

print("==================Lua数据类型转换=====================")


print("=========(toString)可以将布尔类型和数值类型转换为字符串类型=========")
local bVar = false
print(type(tostring(bVar)))

local num = 10
local num1 = 10.0
local num2 = 10.03
print(type(tostring(num)))
print(type(tostring(num1)))
print(type(tostring(num2)))

print("=========(tonumber)可以将非数字的原始值转换为数字类型=========")

print(type(tonumber(10)))
print(type(tonumber("AF",16))..tonumber("AF",16)) --返回16进制数175
print(type(tonumber("0102"))..tonumber("0102"))
print(tonumber("red"))       --nil
print(tonumber(true))          --nil
print(tonumber({x=10,y=20}))    --nil

