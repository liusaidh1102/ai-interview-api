FROM openjdk:8

MAINTAINER Lion Li

RUN mkdir -p  \
    /home/ruanzhu/mianshi/logs \
    /home/ruanzhu/mianshi/temp \
    /home/ruanzhu/mianshi/agent

WORKDIR /home/ruanzhu/mianshi

ENV SERVER_PORT=8089

EXPOSE ${SERVER_PORT}

ADD ./interview-admin.jar ./app.jar

ENTRYPOINT ["java", \
            "-Djava.security.egd=file:/dev/./urandom", \
            "-Dserver.port=${SERVER_PORT}", \
            # 应用名称 如果想区分集群节点监控 改成不同的名称即可
#            "-Dskywalking.agent.service_name=ruoyi-server", \
#            "-javaagent:/ruoyi/skywalking/agent/skywalking-agent.jar", \
            "-jar", "app.jar"]
