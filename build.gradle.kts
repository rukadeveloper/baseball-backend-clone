plugins {
	java
	id("org.springframework.boot") version "3.5.0"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.baseball.comics"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("com.mysql:mysql-connector-j")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// jwt
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

	implementation("org.springframework.boot:spring-boot-starter-security")

	// jakarta
	implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")

	// 크롤링 라이브러리
	implementation("org.jsoup:jsoup:latest.release")
	// 셀리니움 라이브러리
	implementation("org.seleniumhq.selenium:selenium-java:latest.release")

	// javax
	implementation("org.springframework.boot:spring-boot-starter-validation")
}

springBoot {
	mainClass.set("com.baseball.comics.baseball_comics.BaseballComicsApplication")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
