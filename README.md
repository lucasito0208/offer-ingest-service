# Offer Ingest Service

🎯 Microservicio del sistema **Cluster Offers System** encargado de consumir mensajes Kafka con datos de productos y guardarlos en Neo4j.

## ⚙️ Tecnologías
- Java 21
- Spring Boot 3
- Apache Kafka
- Neo4j (vía Spring Data Neo4j)
- Docker

## 🚀 Funcionalidad
Este servicio escucha mensajes JSON desde un topic Kafka, los deserializa en objetos `ProductOfferDto` y:
- Guarda productos como nodos en Neo4j
- Establece relaciones entre productos (`RELATED_WITH`)

## 📦 Estructura esperada del mensaje

```json
{
  "productId": "123",
  "productName": "Zapatillas deportivas",
  "category": "Calzado",
  "price": 59.99,
  "promotion": "20% de descuento",
  "relatedProductIds": ["456", "789"]
}
