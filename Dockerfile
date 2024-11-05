FROM payara/server-web:6.2024.1-jdk17
COPY target/Cadastro-Pessoas.war $DEPLOY_DIR
