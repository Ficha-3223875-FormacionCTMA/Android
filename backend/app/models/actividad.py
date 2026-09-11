from sqlalchemy import Column, Integer, String, Text
from app.database import Base


class Actividad(Base):
    __tablename__ = "actividades"

    id = Column(Integer, primary_key=True, index=True, autoincrement=True)
    titulo = Column(String(200), nullable=False)
    descripcion = Column(Text, nullable=True)
    fecha = Column(String(10), nullable=False)
    prioridad = Column(String(10), nullable=False, default="Media")
    progreso = Column(Integer, nullable=False, default=0)