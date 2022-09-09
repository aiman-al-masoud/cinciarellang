package com.luxlunaris.cincia.backend.throwables;

import java.util.List;

import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.object.AbstractCinciaObject;
import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.backend.object.Magic;
import com.luxlunaris.cincia.frontend.ast.expressions.type.IdentifierType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.OneNameType;
import com.luxlunaris.cincia.frontend.ast.expressions.type.SingleType;
import com.luxlunaris.cincia.frontend.ast.interfaces.Expression;
import com.luxlunaris.cincia.frontend.ast.interfaces.Type;

public class CinciaException extends RuntimeException implements CinciaObject, Type {
	
	protected AbstractCinciaObject object;
	
	
	public CinciaException() {
		object = new AbstractCinciaObject(null);
	}
	
	

	@Override
	public boolean matches(Type other) {
		
		IdentifierType idt = (IdentifierType)other;
//		System.out.println(idt.value);
//		System.out.println(getClass().getSimpleName());
		return idt.value.equals(getClass().getSimpleName()); //TODO: fix this!!!!!!!@
		
	}
	
	@Override
	public Expression simplify() {
		return this;
	}

	@Override
	public Object getValue() {
		return object;
	}

	@Override
	public Type getType() {
		return null;
	}

	@Override
	public CinciaObject get(String key) {
		return object.get(key);
	}

	@Override
	public CinciaObject get(int key) {
		return object.get(key);
	}

	@Override
	public CinciaObject get(Magic key) {
		return object.get(key);
	}

	@Override
	public Type getType(String key) {
		return object.getType(key);
	}

	@Override
	public void set(String key, CinciaObject val, Type type) {
		object.set(key, val, type);
	}

	@Override
	public void set(int key, CinciaObject val, Type type) {
		object.set(key, val, type);
	}

	@Override
	public void set(String key, CinciaObject val) {
		object.set(key, val);
	}

	@Override
	public void set(int key, CinciaObject val) {
		object.set(key, val);
	}

	@Override
	public void set(Magic key, CinciaObject val) {
		object.set(key, val);
	}

	@Override
	public void remove(String key) {
		object.remove(key);
	}

	@Override
	public void setImmutable() {
		object.setImmutable();
	}

	@Override
	public Enviro getEnviro() {
		return object.getEnviro();
	}

	@Override
	public boolean __bool__() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CinciaObject __add__(CinciaObject other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject __sub__(CinciaObject other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject __mul__(CinciaObject other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject __mod__(CinciaObject other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject __div__(CinciaObject other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject __or__(CinciaObject other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject __and__(CinciaObject other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject __lt__(CinciaObject other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject __gt__(CinciaObject other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject __lte__(CinciaObject other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject __gte__(CinciaObject other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject __eq__(CinciaObject other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject __ne__(CinciaObject other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject __not__() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject __str__() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject __neg__() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject __init__(List<CinciaObject> args) {
		return this;
	}

	@Override
	public CinciaObject into(List<CinciaObject> args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject copy(List<CinciaObject> args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CinciaObject freeze(List<CinciaObject> args) {
		// TODO Auto-generated method stub
		return null;
	}

	

}