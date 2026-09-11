from pydantic import BaseModel, Field
from typing import Optional


class ActividadCreate(BaseModel):
    titulo: str = Field(..., min_length=1, max_length=200)
    descripcion: Optional[str] = None
    fecha: str = Field(..., min_length=1, max_length=10)
    prioridad: str = Field("Media", pattern="^(Baja|Media|Alta)$")
    progreso: int = Field(0, ge=0, le=100)


class ActividadUpdate(BaseModel):
    titulo: Optional[str] = Field(None, min_length=1, max_length=200)
    descripcion: Optional[str] = None
    fecha: Optional[str] = Field(None, min_length=1, max_length=10)
    prioridad: Optional[str] = Field(None, pattern="^(Baja|Media|Alta)$")
    progreso: Optional[int] = Field(None, ge=0, le=100)


class ActividadResponse(BaseModel):
    id: int
    titulo: str
    descripcion: Optional[str] = None
    fecha: str
    prioridad: str
    progreso: int

    model_config = {"from_attributes": True}