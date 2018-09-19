# This repository contains the classes that support the usage of patterns for robotic systems

This project is associated with a maven dependency that allows to use the patterns within your project.

To use it add this repository and dependency in your POM.xml

```

	<repository>
			<id>Patterns-mvn-repo</id>
			<url>https://raw.github.com/claudiomenghi/Patterns/mvn-repo</url>
			<snapshots>
				<enabled>true</enabled>
				<updatePolicy>always</updatePolicy>
			</snapshots>
		</repository>

	<dependency>
			<groupId>se.gu</groupId>
			<artifactId>Patterns</artifactId>
			<version>0.0.1-SNAPSHOT</version>
		</dependency>
```

The patterns can be then used as follows.

```
List<PLAtom> atoms=....
StrictOrderedPatrolling pattern=new StrictOrderedPatrolling(atoms);
```

To convert the pattern into an LTL formula you can use the following statement

```
LTLFormula f = pattern.accept(new Pattern2LTL());
```

To see how the LTL formula can be used see [https://github.com/claudiomenghi/LTL](https://github.com/claudiomenghi/LTL)