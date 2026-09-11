from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from typing import List

from app import crud, schemas
from app.database import get_db

router = APIRouter(prefix="/actividades", tags=["Actividades"])


@router.get("/", response_model=List[schemas.ActividadResponse])
def listar_actividades(
    skip: int = 0,
    limit: int = 100,
    db: Session = Depends(get_db)
):
    return crud.get_actividades(db, skip=skip, limit=limit)


@router.get("/{actividad_id}", response_model=schemas.ActividadResponse)
def obtener_actividad(actividad_id: int, db: Session = Depends(get_db)):
    actividad = crud.get_actividad(db, actividad_id)
    if not actividad:
        raise HTTPException(status_code=404, detail="Actividad no encontrada")
    return actividad


@router.post("/", response_model=schemas.ActividadResponse, status_code=201)
def crear_actividad(
    actividad: schemas.ActividadCreate,
    db: Session = Depends(get_db)
):
    return crud.create_actividad(db, actividad)


@router.put("/{actividad_id}", response_model=schemas.ActividadResponse)
def actualizar_actividad(
    actividad_id: int,
    actividad: schemas.ActividadUpdate,
    db: Session = Depends(get_db)
):
    actualizada = crud.update_actividad(db, actividad_id, actividad)
    if not actualizada:
        raise HTTPException(status_code=404, detail="Actividad no encontrada")
    return actualizada


@router.delete("/{actividad_id}", status_code=204)
def eliminar_actividad(actividad_id: int, db: Session = Depends(get_db)):
    eliminada = crud.delete_actividad(db, actividad_id)
    if not eliminada:
        raise HTTPException(status_code=404, detail="Actividad no encontrada")