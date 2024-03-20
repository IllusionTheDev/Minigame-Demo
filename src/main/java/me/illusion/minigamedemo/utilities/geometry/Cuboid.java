package me.illusion.minigamedemo.utilities.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

/**
 * The cuboid class is used to represent a 3D cuboid with decimal precision.
 *
 * @author Illusion
 */
@Getter
@Data
public class Cuboid implements Iterable<BlockVector>, Cloneable {

    private final double minX;
    private final double minY;
    private final double minZ;

    private final double maxX;
    private final double maxY;
    private final double maxZ;

    public Cuboid(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public Cuboid(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public Cuboid(BlockVector min, BlockVector max) {
        this.minX = min.getBlockX();
        this.minY = min.getBlockY();
        this.minZ = min.getBlockZ();
        this.maxX = max.getBlockX();
        this.maxY = max.getBlockY();
        this.maxZ = max.getBlockZ();
    }

    public Cuboid(Location one, Location two) {
        this.minX = Math.min(one.getX(), two.getX());
        this.minY = Math.min(one.getY(), two.getY());
        this.minZ = Math.min(one.getZ(), two.getZ());
        this.maxX = Math.max(one.getX(), two.getX());
        this.maxY = Math.max(one.getY(), two.getY());
        this.maxZ = Math.max(one.getZ(), two.getZ());
    }

    public Cuboid(Location location, double radius) {
        this.minX = location.getX() - radius;
        this.minY = location.getY() - radius;
        this.minZ = location.getZ() - radius;
        this.maxX = location.getX() + radius;
        this.maxY = location.getY() + radius;
        this.maxZ = location.getZ() + radius;
    }

    public Cuboid(Vector vector, double radius) {
        this.minX = vector.getX() - radius;
        this.minY = vector.getY() - radius;
        this.minZ = vector.getZ() - radius;
        this.maxX = vector.getX() + radius;
        this.maxY = vector.getY() + radius;
        this.maxZ = vector.getZ() + radius;
    }

    public Cuboid(Vector min, Vector max) {
        this.minX = min.getX();
        this.minY = min.getY();
        this.minZ = min.getZ();
        this.maxX = max.getX();
        this.maxY = max.getY();
        this.maxZ = max.getZ();
    }

    public Cuboid(Cuboid other, Location center) {
        this.minX = center.getX() - other.getWidth() / 2;
        this.minY = center.getY() - other.getHeight() / 2;
        this.minZ = center.getZ() - other.getLength() / 2;
        this.maxX = center.getX() + other.getWidth() / 2;
        this.maxY = center.getY() + other.getHeight() / 2;
        this.maxZ = center.getZ() + other.getLength() / 2;
    }

    public boolean contains(Vector vector) {
        return vector.getX() >= minX && vector.getX() <= maxX && vector.getY() >= minY && vector.getY() <= maxY && vector.getZ() >= minZ
            && vector.getZ() <= maxZ;
    }

    public boolean contains(double x, double y, double z) {
        return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
    }

    /**
     * Checks if any point in the other cuboid is contained within this cuboid.
     *
     * @param other the other cuboid
     * @return TRUE if any point in the other cuboid is contained within this cuboid, FALSE otherwise
     */
    public boolean contains(Cuboid other) {
        // the other cuboid is not guaranteed to be fully contained within this cuboid
        // none of the corners are guaranteed to be within this cuboid
        // check if any of the corners are within this cuboid
        if (contains(other.minX, other.minY, other.minZ)) {
            return true;
        }
        if (contains(other.minX, other.minY, other.maxZ)) {
            return true;
        }
        if (contains(other.minX, other.maxY, other.minZ)) {
            return true;
        }
        if (contains(other.minX, other.maxY, other.maxZ)) {
            return true;
        }
        if (contains(other.maxX, other.minY, other.minZ)) {
            return true;
        }
        if (contains(other.maxX, other.minY, other.maxZ)) {
            return true;
        }
        if (contains(other.maxX, other.maxY, other.minZ)) {
            return true;
        }
        if (contains(other.maxX, other.maxY, other.maxZ)) {
            return true;
        }

        /*
            .AA.
            BXXB
            .AA.
            intersects at X, but none of the corners are within this cuboid
         */

        return intersectsCuboid(other);
    }

    /**
     * Checks if any point in the other cuboid is contained within this cuboid.
     *
     * @param other the other cuboid
     * @return TRUE if any point in the other cuboid is contained within this cuboid, FALSE otherwise
     */
    public boolean intersectsCuboid(Cuboid other) {
        // copied from https://bukkit.org/threads/checking-if-two-cuboids-intersect.291432/, thanks to @Syd
        if (!intersectsDimension(other.getMinX(), other.getMaxX(), this.getMinX(), this.getMaxX())) {
            return false;
        }

        if (!intersectsDimension(other.getMinY(), other.getMaxY(), this.getMinY(), this.getMaxY())) {
            return false;
        }

        return intersectsDimension(other.getMinZ(), other.getMaxZ(), this.getMinZ(), this.getMaxZ());
    }

    /**
     * Checks if the two dimensions intersect.
     *
     * @param aMin the min value of the first dimension
     * @param aMax the max value of the first dimension
     * @param bMin the min value of the second dimension
     * @param bMax the max value of the second dimension
     * @return TRUE if the two dimensions intersect, FALSE otherwise
     */
    public boolean intersectsDimension(double aMin, double aMax, double bMin, double bMax) {
        return aMin <= bMax && aMax >= bMin;
    }

    /**
     * Checks if a location is contained within this cuboid.
     *
     * @param location the location
     * @return TRUE if the location is contained within this cuboid, FALSE otherwise
     */
    public boolean contains(Location location) {
        return contains(location.getX(), location.getY(), location.getZ());
    }

    /**
     * Obtains the width of the cuboid.
     *
     * @return the width
     */
    public double getWidth() {
        return maxX - minX;
    }

    /**
     * Obtains the height of the cuboid.
     *
     * @return the height
     */
    public double getHeight() {
        return maxY - minY;
    }

    /**
     * Obtains the length of the cuboid.
     *
     * @return the length
     */
    public double getLength() {
        return maxZ - minZ;
    }

    /**
     * Obtains the volume of the cuboid.
     *
     * @return the volume
     */
    public long getVolume() {
        return (long) Math.ceil(getWidth() * getHeight() * getLength());
    }

    /**
     * Obtains the center of the cuboid.
     *
     * @return the center
     */
    public Vector getCenter() {
        return new Vector((minX + maxX) / 2, (minY + maxY) / 2, (minZ + maxZ) / 2);
    }

    /**
     * Obtains the minimum point of the cuboid.
     *
     * @return the minimum point
     */
    public Vector getMin() {
        return new Vector(minX, minY, minZ);
    }

    /**
     * Obtains the maximum point of the cuboid.
     *
     * @return the maximum point
     */
    public Vector getMax() {
        return new Vector(maxX, maxY, maxZ);
    }

    /**
     * Creates a copy of this cuboid.
     *
     * @return the copy
     */
    public Cuboid copy() {
        return new Cuboid(minX, minY, minZ, maxX, maxY, maxZ);
    }

    /**
     * Expands the cuboid by the specified amount, returning a new cuboid.
     *
     * @param x the amount to expand in the x direction
     * @param y the amount to expand in the y direction
     * @param z the amount to expand in the z direction
     * @return the new cuboid
     */
    public Cuboid add(double x, double y, double z) {
        return new Cuboid(minX + x, minY + y, minZ + z, maxX + x, maxY + y, maxZ + z);
    }

    /**
     * Expands the cuboid by the specified amount, returning a new cuboid.
     *
     * @param vector the amount to expand in each direction
     * @return the new cuboid
     */
    public Cuboid add(Vector vector) {
        return add(vector.getX(), vector.getY(), vector.getZ());
    }

    /**
     * Expands the cuboid by the specified amount, returning a new cuboid.
     *
     * @param location the amount to shift the cuboid
     * @return the new cuboid
     */
    public Cuboid add(Location location) {
        return add(location.toVector());
    }

    /**
     * Shrinks the cuboid by the specified amount, returning a new cuboid.
     *
     * @param x the amount to shrink in the x direction
     * @param y the amount to shrink in the y direction
     * @param z the amount to shrink in the z direction
     * @return the new cuboid
     */
    public Cuboid subtract(double x, double y, double z) {
        return new Cuboid(minX - x, minY - y, minZ - z, maxX - x, maxY - y, maxZ - z);
    }

    /**
     * Shrinks the cuboid by the specified amount, returning a new cuboid.
     *
     * @param vector the amount to shrink in each direction
     * @return the new cuboid
     */
    public Cuboid subtract(Vector vector) {
        return subtract(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
    }

    /**
     * Displays a wireframe of the cuboid using particles.
     *
     * @param world the world
     * @param color the color of the particles
     */
    public void displayParticles(World world, Color color) {
        // Basically wire-mesh between all corners
        List<Location> locations = new ArrayList<>();

        locations.add(new Location(world, minX, minY, minZ));
        locations.add(new Location(world, minX, minY, maxZ));
        locations.add(new Location(world, minX, maxY, minZ));
        locations.add(new Location(world, minX, maxY, maxZ));
        locations.add(new Location(world, maxX, minY, minZ));
        locations.add(new Location(world, maxX, minY, maxZ));
        locations.add(new Location(world, maxX, maxY, minZ));
        locations.add(new Location(world, maxX, maxY, maxZ));

        for (int index = 0; index < locations.size(); index++) {
            Location start = locations.get(index);
            for (int index2 = index + 1; index2 < locations.size(); index2++) {
                Location finish = locations.get(index2);
                drawParticleLineBetween(start, finish, color);
            }
        }

    }

    public void render(World world, double offset) {
        Collection<Vector> points = render(offset);

        for (Vector point : points) {
            world.spawnParticle(Particle.EXPLOSION_HUGE, point.getX(), point.getY(), point.getZ(), 1, 0, 0, 0);
        }
    }

    public Collection<Vector> render(double offset) {
        // Render a hollow cuboid
        List<Vector> points = new ArrayList<>();

        for (double x = minX; x <= maxX; x += offset) {
            for (double y = minY; y <= maxY; y += offset) {
                points.add(new Vector(x, y, minZ));
                points.add(new Vector(x, y, maxZ));
            }
        }

        for (double x = minX; x <= maxX; x += offset) {
            for (double z = minZ; z <= maxZ; z += offset) {
                points.add(new Vector(x, minY, z));
                points.add(new Vector(x, maxY, z));
            }
        }

        for (double y = minY; y <= maxY; y += offset) {
            for (double z = minZ; z <= maxZ; z += offset) {
                points.add(new Vector(minX, y, z));
                points.add(new Vector(maxX, y, z));
            }
        }

        return points;
    }

    /**
     * Draws a line of particles between two locations.
     *
     * @param start  the start location
     * @param finish the finish location
     * @param color  the color of the particles
     */
    private void drawParticleLineBetween(Location start, Location finish, Color color) {
        double offset = 0.1;
        double distance = start.distance(finish);
        double steps = distance / offset;

        Vector direction = finish.toVector().subtract(start.toVector()).normalize().multiply(offset);

        for (int index = 0; index < steps; index++) {
            Location location = start.clone().add(direction.clone().multiply(index));
            location.getWorld().spawnParticle(Particle.REDSTONE, location, 1, new Particle.DustOptions(color, 1));
        }
    }

    @Override
    public String toString() {
        return "{Min.x: " + minX + " Max.x: " + maxX + "}" + "{Min.y: " + minY + " Max.y: " + maxY + "}" + "{Min.z: " + minZ + " Max.z: " + maxZ + "}";
    }

    @Override
    public Iterator<BlockVector> iterator() {
        return new CuboidIterator(this);
    }

    @Override
    public Cuboid clone() {
        return copy();
    }

    /**
     * An iterator for a cuboid. Iterates over all blocks in the cuboid.
     */
    private static class CuboidIterator implements Iterator<BlockVector> {

        private final Cuboid cuboid;
        private int index;

        public CuboidIterator(Cuboid cuboid) {
            this.cuboid = cuboid;
        }

        @Override
        public boolean hasNext() {
            return index < cuboid.getVolume();
        }

        @Override
        public BlockVector next() {
            int x = (int) (index % cuboid.getWidth() + cuboid.minX);
            int y = (int) (index / cuboid.getWidth() % cuboid.getHeight() + cuboid.minY);
            int z = (int) (index / cuboid.getWidth() / cuboid.getHeight() + cuboid.minZ);

            index++;

            return new BlockVector(x, y, z);
        }
    }
}
