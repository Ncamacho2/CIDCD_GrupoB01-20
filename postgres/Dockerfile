# Usa la imagen oficial de PostgreSQL
FROM postgres:latest

# Establece la variable de entorno para la contraseña del usuario "postgres"
ENV POSTGRES_PASSWORD=proyecto_ci_2024**
ENV POSTGRES_DB=proyecto_ci

# Copia el script SQL para crear la tabla en el contenedor
COPY init.sql /docker-entrypoint-initdb.d/
