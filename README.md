# Example of using Parboiled2 stack

[![Build Status](https://travis-ci.org/FranklinChen/using-parboiled2-stack.png)](https://travis-ci.org/FranklinChen/using-parboiled2-stack)

My original parser (much larger than this minimalist toy example I
extracted only to illustrate a point) used parser combinators, was
slow, and passed around a lot of contextual information as needed for
semantic predicates and swapping out different lexing behavior. There
were nested symbol tables, for example. Not all the state was cleanly
passed around; some of it was in manually handled global mutable state
with homegrown stacks.

So I tried to write a new parser in Parboiled2 using only the value
stack.

But there seems to be a bug in Parboiled 2.1.0?

Beginning of the stack trace:

```console
org.parboiled2.ValueStackUnderflowException
	at org.parboiled2.ValueStack.pop(ValueStack.scala:104)
	at com.franklinchen.StatefulParser.Word(StatefulParser.scala:67)
	at com.franklinchen.StatefulParser.Line(StatefulParser.scala:48)
	at com.franklinchen.StatefulParser.rec$2(StatefulParser.scala:33)
	at com.franklinchen.StatefulParser.Document(StatefulParser.scala:30)
	at com.franklinchen.StatefulParser.Start(StatefulParser.scala:22)
	at com.franklinchen.StatefulParser$$anonfun$1.apply(StatefulParser.scala:82)
	at com.franklinchen.StatefulParser$$anonfun$1.apply(StatefulParser.scala:82)
	at org.parboiled2.Parser.runRule$1(Parser.scala:142)
	at org.parboiled2.Parser.phase0_initialRun$1(Parser.scala:150)
	at org.parboiled2.Parser.__run(Parser.scala:203)
	at com.franklinchen.StatefulParser$.delayedEndpoint$com$franklinchen$StatefulParser$1(StatefulParser.scala:82)
	at com.franklinchen.StatefulParser$delayedInit$body.apply(StatefulParser.scala:79)
	at scala.Function0$class.apply$mcV$sp(Function0.scala:34)
	at scala.runtime.AbstractFunction0.apply$mcV$sp(AbstractFunction0.scala:12)
	at scala.App$$anonfun$main$1.apply(App.scala:76)
	at scala.App$$anonfun$main$1.apply(App.scala:76)
	at scala.collection.immutable.List.foreach(List.scala:381)
	at scala.collection.generic.TraversableForwarder$class.foreach(TraversableForwarder.scala:35)
	at scala.App$class.main(App.scala:76)
	at com.franklinchen.StatefulParser$.main(StatefulParser.scala:79)
	at com.franklinchen.StatefulParser.main(StatefulParser.scala)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:497)
	at sbt.Run.invokeMain(Run.scala:67)
	at sbt.Run.run0(Run.scala:61)
	at sbt.Run.sbt$Run$$execute$1(Run.scala:51)
	at sbt.Run$$anonfun$run$1.apply$mcV$sp(Run.scala:55)
	at sbt.Run$$anonfun$run$1.apply(Run.scala:55)
	at sbt.Run$$anonfun$run$1.apply(Run.scala:55)
	at sbt.Logger$$anon$4.apply(Logger.scala:85)
	at sbt.TrapExit$App.run(TrapExit.scala:248)
	at java.lang.Thread.run(Thread.java:745)
```
