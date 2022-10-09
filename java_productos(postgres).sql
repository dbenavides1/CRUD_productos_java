CREATE TABLE "Productos" (
  "id_producto" serial4,
  "nombre" varchar(100) NOT NULL,
  "cantidad" int4 NOT NULL,
  "valor_unidad" int8 NOT NULL,
  CONSTRAINT "id_producto" PRIMARY KEY ("id_producto")
);

