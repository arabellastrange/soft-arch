package model.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ManyToManyMap<K, V> {

	private final HashMap<K, Set<V>> kToV;
	private final HashMap<V, Set<K>> vToK;

	public ManyToManyMap() {
		kToV = new HashMap<>();
		vToK = new HashMap<>();

	}

	public void put(K k, V v){
		Set<V> kToVSet = getV(k);
		if(kToVSet == null) {
			kToVSet = new HashSet<>();
			kToVSet.add(v);
			kToV.put(k, kToVSet);
		} else
			kToVSet.add(v);


		Set<K> vToKSet = getK(v);
		if(vToKSet == null) {
			vToKSet = new HashSet<>();
			vToKSet.add(k);
			vToK.put(v, vToKSet);
		} else
			vToKSet.add(k);

	}

	public Set<V> getV(K k) {
		for(K k1 : kToV.keySet())
			if(k1.equals(k))
				return kToV.get(k1);
		return null;
	}

	public Set<K> getK(V v){
		for(V v1 : vToK.keySet())
			if(v1.equals(v))
				return vToK.get(v1);
		return null;
	}

	public boolean containsK(K k){
		for(K k1 : kToV.keySet())
			if(k1.equals(k))
				return true;
		return false;
	}

	public void remove(K k, V v){
		Set<V> kToVSet = kToV.get(k);
		if (kToVSet != null) {
			kToVSet.remove(v);
			if(kToVSet.isEmpty()){
				kToV.remove(k);
			}
		}

		Set<K> vToKSet = vToK.get(v);
		if (vToKSet != null) {
			vToKSet.remove(k);
			if(vToKSet.isEmpty()){
				vToK.remove(v);
			}
		}
	}

	public void removeAllV(K k){
		Set<V> vSet = kToV.remove(k);
		for(V v : vSet){
			Set<K> kSet = vToK.get(v);
			kSet.remove(k);
			if(kSet.isEmpty()){
				vToK.remove(kSet);
			}
		}
	}

	public void clear(){
		kToV.clear();
		vToK.clear();
	}

}
