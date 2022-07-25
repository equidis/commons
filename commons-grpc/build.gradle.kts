import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.plugins
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

val micronautVersion: String by project
val grpcPgvVersion: String by project
val grpcServicesVersion: String by project
val grpcReactorVersion: String by project

plugins {
    id("com.google.protobuf") version "0.8.15"
}

dependencies {
    api(project(":commons-context"))
    api("io.micronaut.grpc:micronaut-grpc-runtime")
    api("io.envoyproxy.protoc-gen-validate:pgv-java-grpc:$grpcPgvVersion")
    api("com.salesforce.servicelibs:reactor-grpc-stub:$grpcReactorVersion")
    compileOnly("io.zipkin.brave:brave-instrumentation-grpc")
    compileOnly("io.micronaut:micronaut-management")
}

protobuf {
    val grpcId = "grpc"
    val reactorId = "reactor"
    protoc {
        artifact = "com.google.protobuf:protoc:3.21.3"
    }
    plugins {
        id(grpcId) {
            artifact = "io.grpc:protoc-gen-grpc-java:1.35.0"
        }
        id(reactorId) {
            artifact = "com.salesforce.servicelibs:reactor-grpc:1.0.1"
        }
    }
    dependencies {
        protobuf(enforcedPlatform("io.micronaut:micronaut-bom:$micronautVersion"))
        protobuf("io.grpc:grpc-services")
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id(grpcId)
                id(reactorId)
            }
        }
    }
}
