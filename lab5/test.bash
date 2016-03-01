#!/bin/bash
x=42

function foo {
   echo $x
}

function bar {
   local x=666
   foo
}

bar

