package com.luxlunaris.cincia.backend.throwables;

import java.util.List;

import com.luxlunaris.cincia.backend.interfaces.CinciaIterable;
import com.luxlunaris.cincia.backend.interfaces.CinciaObject;
import com.luxlunaris.cincia.backend.object.AbstractCinciaObject;
import com.luxlunaris.cincia.backend.object.Enviro;
import com.luxlunaris.cincia.backend.object.Magic;
import com.luxlunaris.cincia.backend.primitives.CinciaBool;
import com.luxlunaris.cincia.backend.primitives.CinciaString;
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
		return idt.value.equals(getClass().getSimpleName()); //TODO: fix this!!!!!!!@
	}

	@Override
	public Expression simplify() {
		return this;
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
	public CinciaBool __bool__() {
		// TODO Auto-generated method stub
		return object.__bool__();
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
	public CinciaBool __eq__(CinciaObject other) {
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
	public CinciaString __str__() {
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
	public CinciaObject as(List<CinciaObject> args) {
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

	@Override
	public Object toJava() {
		return this;
	}

	@Override
	public boolean isImmutable() {
		return object.isImmutable();
	}

	@Override
	public void set(CinciaObject key, CinciaObject val) {
		object.set(key, val);
	}

	@Override
	public CinciaObject get(CinciaObject key) {
		return object.get(key);
	}

	@Override
	public CinciaBool is(List<CinciaObject> args) {
		return new CinciaBool(this == args.get(0)); // this == other
	}

	@Override
	public CinciaObject get(CinciaIterable key) {
		return object.get(key);
	}

	@Override
	public void set(CinciaIterable key, CinciaObject val) {
		set(key, val);
	}

	@Override
	public CinciaString help(List<CinciaObject> args) {
		return object.help(args);
	}	



}
